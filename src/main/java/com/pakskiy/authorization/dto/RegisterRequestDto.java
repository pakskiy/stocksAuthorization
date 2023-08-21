package com.pakskiy.authorization.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RegisterRequestDto {
    @NotBlank
    @Size(min = 3)
    private String username;

    @NotBlank
    @Size(min = 5)
    private String password;

    @Email
    @Size(min = 3)
    private String email;
}