package com.vi5hnu.gobetrotter_api.auth;

import com.vi5hnu.gobetrotter_api.Dto.*;
import com.vi5hnu.gobetrotter_api.Entity.user.OtpModel;
import com.vi5hnu.gobetrotter_api.Entity.user.UserModel;
import com.vi5hnu.gobetrotter_api.Entity.user.VerificationTokenModel;
import com.vi5hnu.gobetrotter_api.annotation.RequireUserWith;
import com.vi5hnu.gobetrotter_api.enums.*;
import com.vi5hnu.gobetrotter_api.events.authEvents.*;
import com.vi5hnu.gobetrotter_api.exceptions.ApiException;
import com.vi5hnu.gobetrotter_api.exceptions.UserAlreadyExistsException;
import com.vi5hnu.gobetrotter_api.services.JwtService;
import com.vi5hnu.gobetrotter_api.services.user.OtpRepository;
import com.vi5hnu.gobetrotter_api.services.user.UserRepository;
import com.vi5hnu.gobetrotter_api.services.user.UserService;
import com.vi5hnu.gobetrotter_api.services.user.VerificationTokenRepository;
import com.vi5hnu.gobetrotter_api.specifications.OtpSpecifications;
import com.vi5hnu.gobetrotter_api.specifications.UserSpecifications;
import com.vi5hnu.gobetrotter_api.specifications.VerificationTokenSpecifications;
import com.vi5hnu.gobetrotter_api.utils.Utils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/users")
@RequiredArgsConstructor
public class UserController {
    @Value("${app.jwt-secret}") private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}") private int jwtExpireMs;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final OtpRepository otpRepository;
    private final ApplicationEventPublisher publisher;
    private final JwtService jwtService;

    @GetMapping(path = "me")
    @RequireUserWith(isEnabled = true,isDeleted = false,isLocked = false)
    public ResponseEntity<Map<String,Object>> getMe(Principal principal) throws ApiException {
        final var user = userService.getActiveUser(principal.getName());
        return ResponseEntity.status(200).body(Map.of("success",true,"data",user));
    }

    @DeleteMapping(path = "")
    @RequireUserWith(isEnabled = true,isDeleted = false,isLocked = false)
    public ResponseEntity<Map<String,Object>> deleteMe(Principal principal) throws ApiException, IOException {
        final var user = userService.deleteUserById(principal.getName());
        return ResponseEntity.status(200).body(Map.of("success",true,"message",String.format("user %s deleted successfully.",user.getUsername())));
    }
    @PostMapping(path = "password/init")
    public ResponseEntity<Map<String,Object>> updatePasswordInit(@RequestBody @Valid UpdatePasswordInit updatePasswordInit, HttpServletResponse httpResponse, Principal principal) throws ApiException {
        final var user=userRepository.findOne(UserSpecifications.activeUserByUsernameOrEmail(updatePasswordInit.getUsernameEmail(),null,null,false)).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,"User not found"));
        if(user.isLocked()) throw new ApiException(HttpStatus.BAD_REQUEST,"Account suspended");
        else if(!user.isEnabled()) throw new ApiException(HttpStatus.BAD_REQUEST,"Account not verified");

        //create verification otp for user
        final String otp= Utils.generateOtp();
        //save otp for the user

        LocalDateTime timestampAfter15Min = LocalDateTime.now().plusMinutes(OtpModel.EXPIRE_AFTER_MINS);
        final OtpModel otpModel=OtpModel.builder()
                .userId(user.getId())
                .reason(OtpReason.PASSWORD_UPDATE)
                .expireAt(Timestamp.valueOf(timestampAfter15Min))
                .status(OtpStatus.UN_USED)
                .otp(otp)
                .build();
        final OtpModel savedOtp = otpRepository.save(otpModel);
        //send email
        publisher.publishEvent(new PasswordUpdateInit(UserModel.toDto(user),otp));

        //send response
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",true,"message","check your email for otp to verify account."));
    }

    @PatchMapping(path = "password/complete")
    @RequireUserWith(isEnabled = true,isDeleted = false,isLocked = false)
    public ResponseEntity<Map<String,Object>> updatePasswordComplete(@RequestBody @Valid UpdatePasswordDto updatePasswordDto, HttpServletResponse httpResponse, Principal principal) throws ApiException {
        final List<OtpModel> latestUnUsedActiveOtps=otpRepository.findAll(OtpSpecifications.getLatestActiveOtps(principal.getName(), OtpReason.PASSWORD_UPDATE,OtpStatus.UN_USED));
        if(latestUnUsedActiveOtps.isEmpty() || !latestUnUsedActiveOtps.get(0).getOtp().equals(updatePasswordDto.getOtp())) throw new ApiException(HttpStatus.BAD_REQUEST,"Invalid otp");

        //update password
        UserModel userModel = userService.updatePasswordById(principal.getName(),updatePasswordDto.getOldPassword(),updatePasswordDto.getNewPassword(),updatePasswordDto.getConfirmPassword());

        //update otp status
        for(int otpIdx=0;otpIdx<latestUnUsedActiveOtps.size();otpIdx++){
            if(otpIdx==0) latestUnUsedActiveOtps.get(otpIdx).setStatus(OtpStatus.USED);
            else latestUnUsedActiveOtps.get(otpIdx).setStatus(OtpStatus.REVOKED);
        }
        otpRepository.saveAll(latestUnUsedActiveOtps);

        //renew token
        final String jwtToken=jwtService.generateJwtToken(UserModel.toDto(userModel),jwtExpireMs,jwtSecret);
        httpResponse.addHeader(HttpHeaders.SET_COOKIE,Utils.generateCookie(jwtToken, jwtExpireMs, "/").toString());

        //security alert
        publisher.publishEvent(new PasswordUpdateComplete(userModel));
        return ResponseEntity.status(200).body(Map.of("success",true,"message",String.format("password update for user %s successful.",userModel.getUsername())));
    }


    @PostMapping(path = "login")
    public ResponseEntity<Map<String,Object>> login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse httpResponse) throws ApiException {
        final UserModel userModel=this.userService.findByUsernameOrEmail(loginRequestDto.getUsernameEmail(),null,false,null).orElseThrow(()->new ApiException(HttpStatus.BAD_REQUEST,"Invalid username/Email/password"));

        if( !(userModel.getUsername().equals(loginRequestDto.getUsernameEmail()) || userModel.getEmail().equals(loginRequestDto.getUsernameEmail())) || !passwordEncoder.matches(loginRequestDto.getPassword(), userModel.getPassword())){
            throw new ApiException(HttpStatus.UNAUTHORIZED,"Invalid username/email/password.");
        }

        if(userModel.isLocked()){
            throw new ApiException(HttpStatus.FORBIDDEN,"Your Account has been suspended");
        }else if(!userModel.isEnabled()){
            throw new ApiException(HttpStatus.BAD_REQUEST,"Account Not Verified");
        }

        final String jwtToken=jwtService.generateJwtToken(UserModel.toDto(userModel),jwtExpireMs,jwtSecret);
        final var cookie=Utils.generateCookie(jwtToken, jwtExpireMs, "/");
        httpResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(Map.of("success",true,"data",UserModel.toDto(userModel),"message","login success"));
    }

    @GetMapping(path = "logout")
    public ResponseEntity<Map<String,Object>> logout(HttpServletResponse httpResponse,Principal principal) throws ApiException {
        final var user=userRepository.findOne(UserSpecifications.activeUserById(principal.getName())).orElseThrow(()->new ApiException(HttpStatus.BAD_REQUEST,"Failed to logout,user not found"));

        final Cookie cookie=new Cookie("jwt", null);
//        cookie.setSecure(true);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        httpResponse.addCookie(cookie);
        return ResponseEntity.ok(Map.of("success",true,"message",String.format("logout successful - %s",user.getUsername())));
    }

    @PostMapping(path = "register")
    public ResponseEntity<Map<String,Object>> register(@RequestBody() @Valid RegisterRequestDto userInfo, HttpServletRequest httpServletRequest) throws UserAlreadyExistsException, IOException, ApiException {
        //save user
        final UserModel userModel=userService.createUser(userInfo);

        final var token=Utils.generateToken();

        //save verification token for the user
        Timestamp expireAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(VerificationTokenModel.EXPIRE_AFTER_MINS));
        final VerificationTokenModel vtm=VerificationTokenModel.builder()
                .token(token)
                .reason(TokenReason.ACCOUNT_VERIFICATION)
                .expireAt(expireAt)
                .status(TokenStatus.UN_USED)
                .userId(userModel.getId())
                .build();
        verificationTokenRepository.save(vtm);

        //send email
        publisher.publishEvent(new RegistrationVerificationEvent(userModel,token, this.getVerificationUrl(httpServletRequest)));

        //send response
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success",true,"message","check your email to verify account."));
    }

    private String getVerificationUrl(@NonNull HttpServletRequest httpServletRequest){
        return httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + "/api/v1/users/verify";
    }

    @GetMapping(path = "re-verify")
    public ResponseEntity<Map<String,Object>> reVerifyUser(@RequestParam(name = "email") String email,HttpServletRequest httpServletRequest) throws ApiException {
        //if token already exists delete it... not required-> auto delete
        final UserModel userModel=this.userService.findByUsernameOrEmail(email,null,false,null).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,"User does not exists"));

        if(userModel.isEnabled()){
            return ResponseEntity.ok(Map.of("success",true,"message","user already verified"));
        }else if(userModel.isLocked()) throw new ApiException(HttpStatus.BAD_REQUEST,"Account suspended");

        final var token=Utils.generateToken();
        //save verification token for the user
        Timestamp expireAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(VerificationTokenModel.EXPIRE_AFTER_MINS));
        final VerificationTokenModel vtm=VerificationTokenModel.builder()
                .token(token)
                .reason(TokenReason.ACCOUNT_VERIFICATION)
                .status(TokenStatus.UN_USED)
                .expireAt(expireAt)
                .userId(userModel.getId())
                .build();
        verificationTokenRepository.save(vtm);

        //send email
        final String verificationUrl= httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + "/api/v1/users/verify";
        publisher.publishEvent(new RegistrationVerificationEvent(userModel,token,verificationUrl));
        return ResponseEntity.ok(Map.of("success",true,"message","Email Sent!"));
    }

    @GetMapping(path = "verify")
    public ResponseEntity<String> verifyUser(@RequestParam(name = "token",defaultValue = "") String verificationToken) throws ApiException {
        final var activeUnusedToken=verificationTokenRepository.findOne(VerificationTokenSpecifications.getActiveToken(verificationToken, TokenReason.ACCOUNT_VERIFICATION, TokenStatus.UN_USED)).orElseThrow(()->new ApiException(HttpStatus.BAD_REQUEST,"Invalid token"));

        final UserModel userModel=userRepository.findById(activeUnusedToken.getUserId()).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,"failed to verify, user not found."));

        if(userModel.isDeleted()) throw new ApiException(HttpStatus.NOT_FOUND,"User does not exists");
        else if(userModel.isLocked()) throw new ApiException(HttpStatus.FORBIDDEN,"Account suspended");
        else if(userModel.isEnabled()) throw new ApiException(HttpStatus.ALREADY_REPORTED,"Account already verified");

        //set enabled
        userModel.setEnabled(true);
        userRepository.save(userModel);

        activeUnusedToken.setStatus(TokenStatus.USED);
        verificationTokenRepository.save(activeUnusedToken);

        return ResponseEntity.ok("Verification success!");
    }
    @PostMapping(path = "jwt/verify")
    public ResponseEntity<String> verifyJwtToken(HttpServletRequest httpServletRequest) throws ApiException {
        final var token=jwtService.getTokenFromRequest(httpServletRequest);
        final var claims=jwtService.getClaims(token,jwtSecret);
        final var user=userRepository.findOne(UserSpecifications.activeUserById(claims.getSubject(),null,null,false)).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,"User not found"));

        //this should never pass...as token will be created for enabled account only
        if(user.isLocked()) throw new ApiException(HttpStatus.BAD_REQUEST,"Account Suspended");
        else if(!user.isEnabled()) throw new ApiException(HttpStatus.BAD_REQUEST,"Account not verified");
        return ResponseEntity.ok("Verification success!");
    }

    @PostMapping(path = "forgot-password")
    public ResponseEntity<Map<String,Object>> forgotPassword(@RequestBody @Valid ForgotPasswordRequestDto forgotPassword, HttpServletRequest httpServletRequest) throws ApiException {
        //check if such user exists
        final UserModel userModel=userService.findByUsernameOrEmail(forgotPassword.getUsernameEmail(),null,false,true).orElseThrow(()->new ApiException(HttpStatus.NOT_FOUND,String.format("user %s does not exists.",forgotPassword.getUsernameEmail())));
        if(userModel.isLocked()) throw new ApiException(HttpStatus.FORBIDDEN,"Account suspended");

        final var otp=Utils.generateOtp();
        Timestamp expireAt = Timestamp.valueOf(LocalDateTime.now().plusMinutes(OtpModel.EXPIRE_AFTER_MINS));
        final var otpModel = OtpModel.builder()
                .otp(otp)
                .reason(OtpReason.PASSWORD_FORGOT)
                .status(OtpStatus.UN_USED)
                .userId(userModel.getId())
                .expireAt(expireAt)
                .build();
        otpRepository.save(otpModel);

        //send otp email
        publisher.publishEvent(new OtpEvent(userModel.getId(),userModel.getFirstName(),userModel.getEmail(),otp));
        return ResponseEntity.status(200).body(Map.of("success",true,"message","please enter the otp sent via mail!"));
    }

    @PostMapping(path = "reset-password")
    public ResponseEntity<Map<String,Object>> resetPassword(@RequestBody @Valid ResetPasswordRequestDto resetPassword, HttpServletRequest httpServletRequest) throws ApiException {
        if(!resetPassword.getPassword().equals(resetPassword.getConfirmPassword())) throw new ApiException(HttpStatus.BAD_REQUEST,"password and confirm password should be same");

        final var user=userService.findByUsernameOrEmail(resetPassword.getUsernameEmail(),null,false,true).orElseThrow(()->new ApiException(HttpStatus.BAD_REQUEST,"User does not exists"));
        if(user.isLocked()) throw new ApiException(HttpStatus.FORBIDDEN,"Account suspended");

        List<OtpModel> latestActiveUnusedOtp=this.otpRepository.findAll(OtpSpecifications.getLatestActiveOtps(user.getId(),OtpReason.PASSWORD_FORGOT,OtpStatus.UN_USED));
        if(latestActiveUnusedOtp.isEmpty() || !latestActiveUnusedOtp.get(0).getOtp().equals(resetPassword.getOtp())) throw new ApiException(HttpStatus.BAD_REQUEST,"Otp expired/invalid");

        //update user
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(resetPassword.getPassword()));
        user.setPasswordUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);

        //update otp
        for(int otpIdx=0;otpIdx<latestActiveUnusedOtp.size();otpIdx++){
            if(otpIdx==0) latestActiveUnusedOtp.get(otpIdx).setStatus(OtpStatus.USED);
            else latestActiveUnusedOtp.get(otpIdx).setStatus(OtpStatus.REVOKED);
        }
        otpRepository.saveAll(latestActiveUnusedOtp);

        //send alert email
        publisher.publishEvent(new AlertEvent(user.getFirstName(), user.getEmail(), "you password has been changed successfully..."));
        return ResponseEntity.status(200).body(Map.of("success",true,"message","password changed successfully..."));//give option if the owner doesnt changed his password
    }
}