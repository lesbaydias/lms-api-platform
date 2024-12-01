package com.example.lms_platfom.dto;

import com.example.lms_platfom.enums.QuestionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionDto {
    private String content;
    private QuestionType type;
    private List<String> options;
    private String correctAnswer;
    private Long testId;
}
