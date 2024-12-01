package com.example.lms_platfom.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyCodeRequest {
    private String phoneNumber;
    private String code;
}