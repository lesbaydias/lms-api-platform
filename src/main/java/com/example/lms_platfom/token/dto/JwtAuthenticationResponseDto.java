package com.example.lms_platfom.token.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class JwtAuthenticationResponseDto {
    Date timestamp;
    String username;
    String accessToken;
    String refreshToken;
}
