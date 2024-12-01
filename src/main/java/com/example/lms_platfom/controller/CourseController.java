package com.example.lms_platfom.controller;


import com.example.lms_platfom.dto.CourseDto;
import com.example.lms_platfom.model.Course;
import com.example.lms_platfom.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/create-course")
    public Course createCourse(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestBody CourseDto courseDto){
        return courseService.create(courseDto, userDetails);
    }
    @GetMapping("/all-courses")
    public List<Course> getAllCourses(){
        return courseService.findAll();
    }

    @GetMapping("/course/{courseId}")
    public Course getCourseById(@PathVariable Long courseId){
        return courseService.findById(courseId);
    }

}
