package com.example.lms_platfom.dto;

import com.example.lms_platfom.enums.AssignmentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AssignmentDto {
    private String title;
    private String description;
    private Long courseId;
    private Long lessonId;
    private LocalDateTime deadline;
    private AssignmentType assignmentType;
}
