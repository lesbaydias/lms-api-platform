package com.example.lms_platfom.controller;

import com.example.lms_platfom.dto.LessonDto;
import com.example.lms_platfom.model.Lesson;
import com.example.lms_platfom.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {
    private final LessonService lessonService;


    @PostMapping("/create")
    public Lesson create(@AuthenticationPrincipal UserDetails userDetails,
                         @RequestBody LessonDto lessonDto) {
        return lessonService.create(userDetails, lessonDto);
    }

    @GetMapping("/{lessonId}")
    public Lesson getLessonById(@PathVariable Long lessonId) {
        return lessonService.getLessonById(lessonId);
    }

    @GetMapping("/in-course/{courseId}")
    public List<Lesson> getLessonsByCourseId(@PathVariable Long courseId) {
        return lessonService.getLessonsOfCourse(courseId);
    }
}
