package com.example.lms_platfom.dto;

import com.example.lms_platfom.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterDto {
    private String email;
    private String firstname;
    private String lastname;
    private LocalDateTime birthdayDate;
    private String city;
    private String password;
    private String phoneNumber;
    private Role roles;
}