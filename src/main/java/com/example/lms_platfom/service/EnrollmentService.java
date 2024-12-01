package com.example.lms_platfom.service;

import com.example.lms_platfom.enums.CourseStatus;
import com.example.lms_platfom.model.Course;
import com.example.lms_platfom.model.Enrollment;
import com.example.lms_platfom.model.User;
import com.example.lms_platfom.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserService userService;
    private final CourseService courseService;

    public Enrollment createEnrollment(UserDetails userDetails, Long courseId) {
        Course course = courseService.findById(courseId);
        User user = userService.findByEmail(userDetails.getUsername());

        Enrollment enrollment = Enrollment.builder()
                .course(course)
                .user(user)
                .createdAt(LocalDateTime.now())
                .status(CourseStatus.START)
                .build();

        return enrollmentRepository.save(enrollment);
    }
}
