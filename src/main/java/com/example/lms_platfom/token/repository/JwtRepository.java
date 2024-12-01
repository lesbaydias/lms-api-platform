package com.example.lms_platfom.token.repository;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtRepository {
    String generateToken(UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extraClaim, UserDetails userDetails);
}