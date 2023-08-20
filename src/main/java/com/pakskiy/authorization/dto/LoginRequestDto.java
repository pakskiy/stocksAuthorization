package com.pakskiy.authorization.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @Email
    @Size(min = 3, message = "Email length is small")
    private String email;

    @NotBlank(message = "Password is empty")
    @Size(min = 5, message = "Password length is small")
    private String password;
}