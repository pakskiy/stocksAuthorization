package com.pakskiy.authorization.service;

import com.pakskiy.authorization.config.JwtService;
import com.pakskiy.authorization.dto.LoginRequestDto;
import com.pakskiy.authorization.dto.LoginResponseDto;
import com.pakskiy.authorization.dto.RegisterRequestDto;
import com.pakskiy.authorization.dto.RegisterResponseDto;
import com.pakskiy.authorization.exception.RecordNotFoundException;
import com.pakskiy.authorization.exception.RecordSaveException;
import com.pakskiy.authorization.exception.WrongFormatException;
import com.pakskiy.authorization.model.User;
import com.pakskiy.authorization.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public RegisterResponseDto register(RegisterRequestDto request) {
        try {
            var user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            return userRepository.save(user).getRegisterResponseDto();
        }catch (Exception e){
            throw new RecordSaveException("Error saving user");
        }
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword()
                    )
            );
        } catch (BadCredentialsException bce){
            log.error(bce.getMessage());
            throw new RecordNotFoundException(bce.getMessage());
        } catch (CredentialsExpiredException cee){
            log.error(cee.getMessage());
            throw new WrongFormatException(cee.getMessage());
        }

        return LoginResponseDto.builder()
                .token(userRepository
                        .findByEmail(request.getEmail())
                        .map(jwtService::generateToken)
                        .orElseThrow(() -> new RecordNotFoundException("User " + request.getEmail() + "not found")))
                .build();
    }
}