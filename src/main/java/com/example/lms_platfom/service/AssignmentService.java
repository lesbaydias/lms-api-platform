package com.example.lms_platfom.service;

import com.example.lms_platfom.dto.AssignmentDto;
import com.example.lms_platfom.enums.Role;
import com.example.lms_platfom.model.Assignment;
import com.example.lms_platfom.model.Course;
import com.example.lms_platfom.model.Lesson;
import com.example.lms_platfom.model.User;
import com.example.lms_platfom.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final CourseService courseService;
    private final LessonService lessonService;

    public Assignment save(AssignmentDto assignmentDto, UserDetails userDetails) {
        Course course = courseService.findById(assignmentDto.getCourseId());
        Lesson lesson = lessonService.getLessonById(assignmentDto.getLessonId());

        if(!course.getTeacher().getUser().getEmail().equals(userDetails.getUsername()))
            throw new RuntimeException("You can not add assignment for this course");

        if(!lesson.getCourse().getTeacher().getUser().getUsername().equals(userDetails.getUsername()))
            throw new RuntimeException("You can not add assignment for this course");



        Assignment assignment = Assignment.builder()
                .title(assignmentDto.getTitle())
                .course(course)
                .lesson(lesson)
                .deadline(assignmentDto.getDeadline())
                .description(assignmentDto.getDescription())
                .assignmentType(assignmentDto.getAssignmentType())
                .build();
        return assignmentRepository.save(assignment);
    }


    public Assignment findById(Long id) {
        return assignmentRepository.findById(id).orElse(null);
    }
}
