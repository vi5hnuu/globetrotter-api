package com.vi5hnu.gobetrotter_api.Dto;

import com.vi5hnu.gobetrotter_api.Dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private UserDto user;
    private String token;
}
