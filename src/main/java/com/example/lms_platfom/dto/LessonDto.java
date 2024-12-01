package com.example.lms_platfom.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LessonDto {
    private String title;
    private String description;
    private String content;
    private Long courseId;
}
