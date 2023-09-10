package com.pakskiy.authorization.rest;

import com.pakskiy.authorization.dto.LoginRequestDto;
import com.pakskiy.authorization.dto.LoginResponseDto;
import com.pakskiy.authorization.dto.RegisterRequestDto;
import com.pakskiy.authorization.dto.RegisterResponseDto;
import com.pakskiy.authorization.service.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class AuthRestControllerV1 {
    private final AuthServiceImpl authServiceImpl;

    @GetMapping("/status")
    public String status() {
        return "200 OK";
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto request) {
        return ResponseEntity.ok(authServiceImpl.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        return ResponseEntity.ok(authServiceImpl.login(request));
    }
}
