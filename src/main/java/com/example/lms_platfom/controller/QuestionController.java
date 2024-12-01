package com.example.lms_platfom.controller;

import com.example.lms_platfom.dto.QuestionDto;
import com.example.lms_platfom.model.Question;
import com.example.lms_platfom.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/create")
    public Question createQuestion(@AuthenticationPrincipal UserDetails userDetails,
                                   @RequestBody QuestionDto questionDto
    ) {
        return questionService.save(questionDto, userDetails);
    }
}
