package com.example.lms_platfom.repository;

import com.example.lms_platfom.dto.LoginDto;
import com.example.lms_platfom.dto.RegisterDto;
import com.example.lms_platfom.model.User;
import com.example.lms_platfom.token.dto.JwtAuthenticationResponseDto;
import com.example.lms_platfom.token.dto.RefreshTokenRequestDto;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository {
    User register(RegisterDto registerDto) ;
    JwtAuthenticationResponseDto login(LoginDto loginDto) throws Exception;
    JwtAuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest);
}