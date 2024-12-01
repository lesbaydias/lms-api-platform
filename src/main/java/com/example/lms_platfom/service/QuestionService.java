package com.example.lms_platfom.service;

import com.example.lms_platfom.dto.QuestionDto;
import com.example.lms_platfom.enums.QuestionType;
import com.example.lms_platfom.model.Question;
import com.example.lms_platfom.model.Test;
import com.example.lms_platfom.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TestService testService;

    public Question save(QuestionDto dto, UserDetails userDetails) {
        Test test = testService.getTestById(dto.getTestId());

        if(test == null)
            throw new RuntimeException("Test not found");

        if (!test.getAssignment().getCourse().getTeacher().getUser().getEmail().equals(userDetails.getUsername()))
            throw new RuntimeException("You can not add a question to this test");


        Question question = Question.builder()
                .content(dto.getContent())
                .type(dto.getType())
                .test(test)
                .build();

        if(dto.getType().name().equals(QuestionType.OPEN.name())) {
            question.setOptions(null);
            question.setCorrectAnswer("");
        }

        if(dto.getType().name().equals(QuestionType.MULTIPLE_CHOICE.name())){
            question.setOptions(dto.getOptions());
            question.setCorrectAnswer(dto.getCorrectAnswer());
        }
        return questionRepository.save(question);

    }
}
