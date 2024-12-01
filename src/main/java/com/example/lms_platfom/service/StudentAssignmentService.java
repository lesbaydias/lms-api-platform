package com.example.lms_platfom.service;

import com.example.lms_platfom.dto.StudentAssignmentDto;
import com.example.lms_platfom.enums.AssignmentStatus;
import com.example.lms_platfom.enums.AssignmentType;
import com.example.lms_platfom.model.Assignment;
import com.example.lms_platfom.model.StudentAssignment;
import com.example.lms_platfom.model.User;
import com.example.lms_platfom.repository.StudentAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentAssignmentService {
    private final StudentAssignmentRepository studentAssignmentRepository;
    private final AssignmentService assignmentService;
    private final UserService userService;


    public StudentAssignment save(StudentAssignmentDto studentAssignmentDto, UserDetails userDetails) {
        Assignment assignment = assignmentService.findById(studentAssignmentDto.getAssignmentId());
        if(!assignment.getCourse().getTeacher().getUser().getEmail().equals(userDetails.getUsername()))
            throw new RuntimeException("You can not add assignment to a student");

        if(assignment.getAssignmentType().name().equals(AssignmentType.QUIZ.name()))
            throw new RuntimeException("This is not a assignment. This is a quiz type");


        User user = userService.findById(studentAssignmentDto.getUserId());

        StudentAssignment studentAssignment = StudentAssignment.builder()
                .user(user)
                .assignment(assignment)
                .status(AssignmentStatus.NOT_GRADED)
                .grade(null)
                .submission(null)
                .submittedAt(null)
                .build();

        return studentAssignmentRepository.save(studentAssignment);
    }
}
