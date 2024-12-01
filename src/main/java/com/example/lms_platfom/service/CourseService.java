package com.example.lms_platfom.service;

import com.example.lms_platfom.dto.CourseDto;
import com.example.lms_platfom.model.Course;
import com.example.lms_platfom.model.Teacher;
import com.example.lms_platfom.model.User;
import com.example.lms_platfom.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final TeacherService teacherService;
    private final UserService userService;

    public Course create(CourseDto courseDto, UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());

        Teacher teacher = teacherService.getByUserId(user.getUserId());

        log.info("Teacher: {}", teacher.getTeacherId());

        Course course = Course.builder()
                .accessLevel(courseDto.getAccessLevel())
                .title(courseDto.getTitle())
                .teacher(teacher)
                .description(courseDto.getDescription())
                .difficulty(courseDto.getDifficulty())
                .specialization(courseDto.getSpecialization())
                .build();
        courseRepository.save(course);
        return course;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(()->
                new RuntimeException("Course not found"));
    }
}
