package com.pakskiy.authorization.controller;

import com.pakskiy.authorization.dto.LoginRequestDto;
import com.pakskiy.authorization.dto.LoginResponseDto;
import com.pakskiy.authorization.dto.RegisterRequestDto;
import com.pakskiy.authorization.service.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceImpl authServiceImpl;

    @GetMapping("/status")
    public String status() {
        return "200 OK";
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Validated RegisterRequestDto request) {
        authServiceImpl.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Validated LoginRequestDto request) {
        return ResponseEntity.ok(authServiceImpl.login(request));
    }
}
