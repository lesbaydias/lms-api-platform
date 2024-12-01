package com.example.lms_platfom.dto;

import com.example.lms_platfom.enums.AccessLevel;
import com.example.lms_platfom.enums.DifficultyLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDto {
    private String title;
    private String description;
    private DifficultyLevel difficulty;
    private String specialization;
    private AccessLevel accessLevel;
}
