package com.example.lms_platfom.controller;

import com.example.lms_platfom.model.Enrollment;
import com.example.lms_platfom.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping("/enrollToCourse/{courseId}")
    public Enrollment createEnrollment(@AuthenticationPrincipal UserDetails userDetails,
                                       @PathVariable Long courseId
    ) {
        return enrollmentService.createEnrollment(userDetails, courseId);
    }
}
