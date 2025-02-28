package com.vi5hnu.gobetrotter_api.aspects;

import com.vi5hnu.gobetrotter_api.Entity.user.UserModel;
import com.vi5hnu.gobetrotter_api.annotation.RequireUserWith;
import com.vi5hnu.gobetrotter_api.exceptions.ApiException;
import com.vi5hnu.gobetrotter_api.services.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Aspect
@Component
@RequiredArgsConstructor
public class RequireUserWithAspect {

    private final UserRepository userRepository;


    @Before("@annotation(requireUserWith)")
    public void checkIfUserHas( RequireUserWith requireUserWith) throws ApiException {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        UserModel user=userRepository.findById(principal.getName()).orElseThrow(()->new ApiException(HttpStatus.BAD_REQUEST,"user not found."));
        if (requireUserWith.isEnabled() != user.isEnabled()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, String.format("User is %s",user.isEnabled() ? "verified":"not verified"));
        }else if (requireUserWith.isLocked() != user.isLocked()) {
            throw new ApiException(HttpStatus.FORBIDDEN, String.format("User is %s.",user.isLocked() ? "blocked, Please contact the administrator for more":"not blocked"));
        }else if (!requireUserWith.isDeleted() && user.isDeleted()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "user not found.");
        }
    }
}