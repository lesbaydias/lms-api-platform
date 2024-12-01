package com.example.lms_platfom.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserUpdateDto {
    private String firstname;
    private String lastname;
    private String birthdayDate;
    private String city;
}
