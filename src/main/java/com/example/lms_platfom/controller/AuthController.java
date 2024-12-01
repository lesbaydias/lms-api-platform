package com.example.lms_platfom.controller;

import com.example.lms_platfom.dto.*;
import com.example.lms_platfom.model.User;
import com.example.lms_platfom.repository.AuthenticationRepository;
import com.example.lms_platfom.service.PasswordRecoveryService;
import com.example.lms_platfom.token.dto.JwtAuthenticationResponseDto;
import com.example.lms_platfom.token.dto.RefreshTokenRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationRepository authenticationRepository;
    private final PasswordRecoveryService passwordRecoveryService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@RequestBody RegisterDto registerDto){
        return ResponseEntity.ok(authenticationRepository.register(registerDto));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<JwtAuthenticationResponseDto> login(@RequestBody LoginDto loginDto) throws Exception {
        return ResponseEntity.ok(authenticationRepository.login(loginDto));
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<JwtAuthenticationResponseDto> refresh(
            @RequestBody RefreshTokenRequestDto refreshTokenRequest){
        return ResponseEntity.ok(authenticationRepository.refreshToken(refreshTokenRequest));
    }
    @PostMapping("/send-code")
    public ResponseEntity<String> sendRecoveryCode(
            @RequestBody PasswordRecoveryRequestNumber request
    ) {
        passwordRecoveryService.sendRecoveryCode(request);

        return ResponseEntity.ok("Recovery code sent");
    }

    @PostMapping("/send-code-email")
    public ResponseEntity<String> sendRecoveryCodeEmail(
            @RequestBody PasswordRecoveryRequestEmail request
    ) {
        passwordRecoveryService.sendRecoveryCodeEmail(request);

        return ResponseEntity.ok("Recovery code sent");
    }

    @PostMapping("/verify-code")
    public ResponseEntity<String> verifyCode(@RequestBody VerifyCodeRequest request) {
        passwordRecoveryService.verifyCode(request);
        return ResponseEntity.ok("Code verified");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        passwordRecoveryService.resetPassword(request);
        return ResponseEntity.ok("Password reset successfully");
    }
}
