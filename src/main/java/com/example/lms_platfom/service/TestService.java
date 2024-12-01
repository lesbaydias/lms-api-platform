package com.example.lms_platfom.service;

import com.example.lms_platfom.dto.TestDto;
import com.example.lms_platfom.enums.AssignmentType;
import com.example.lms_platfom.model.Assignment;
import com.example.lms_platfom.model.Test;
import com.example.lms_platfom.model.User;
import com.example.lms_platfom.model.UserTest;
import com.example.lms_platfom.repository.TestRepository;
import com.example.lms_platfom.repository.UserTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final AssignmentService assignmentService;
    private final UserService userService;
    private final UserTestRepository userTestRepository;

    public Test save(TestDto testDto, UserDetails userDetails) {
        Assignment assignment = assignmentService.findById(testDto.getAssignmentId());

        if(!assignment.getCourse().getTeacher().getUser().getEmail().equals(userDetails.getUsername()))
            throw new RuntimeException("You can not add test for this assignment");

        if(assignment.getAssignmentType().name().equals(AssignmentType.ASSIGNMENT.name()))
            throw new RuntimeException("This is assignment type. Not test");


        return testRepository.save(Test.builder()
                .title(testDto.getTitle())
                .assignment(assignment)
                .build());
    }

    public void addUserToTest(Long userId, Long testId, UserDetails userDetails) {
        Test test = testRepository.findById(testId).orElse(null);
        User user = userService.findById(userId);

        if(test == null || user == null)
            throw new RuntimeException("Test not found");

        if (!test.getAssignment().getCourse().getTeacher().getUser().getEmail().equals(userDetails.getUsername()))
            throw new RuntimeException("You can not add user for this test");

        UserTest userTest = UserTest.builder()
                .user(user)
                .test(test)
                .answer(null)
                .grade(null)
                .submittedAt(null)
                .build();

        userTestRepository.save(userTest);
    }


    public Test getTestById(Long testId) {
        return testRepository.findById(testId).orElse(null);
    }
}
