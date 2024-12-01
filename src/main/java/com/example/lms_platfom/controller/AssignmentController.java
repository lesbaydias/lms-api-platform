package com.example.lms_platfom.controller;

import com.example.lms_platfom.dto.AssignmentDto;
import com.example.lms_platfom.model.Assignment;
import com.example.lms_platfom.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignment")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @PostMapping("/create")
    public Assignment createAssignment(@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestBody AssignmentDto assignmentDto
    ) {
        return assignmentService.save(assignmentDto, userDetails);
    }
}
