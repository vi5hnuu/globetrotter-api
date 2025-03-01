package com.vi5hnu.gobetrotter_api.utils;

import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseCookie;

import java.util.Random;
import java.util.UUID;

public class Utils {
    public static ResponseCookie generateCookie(String jwtToken,int jwtExpireMs,String path,boolean secure){
        return ResponseCookie.from("jwt", jwtToken)
                .httpOnly(true)
                .secure(secure)
                .maxAge(jwtExpireMs/1000)
                .path(path)
                .domain("")
                .sameSite("None")
                .build();
    }

    public static String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generates a random 6-digit number
        return String.valueOf(otp);
    }

    public static String generateToken(){
        return UUID.randomUUID().toString();
    }
}
