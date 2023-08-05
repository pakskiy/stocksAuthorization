package com.pakskiy.authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequestDto {
    private String username;
    private String password;
    private String email;
}