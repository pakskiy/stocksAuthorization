package com.pakskiy.authorization.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    @JsonProperty("token")
    private String token;
}
