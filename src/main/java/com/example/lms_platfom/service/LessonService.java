package com.example.lms_platfom.service;

import com.example.lms_platfom.dto.LessonDto;
import com.example.lms_platfom.model.Course;
import com.example.lms_platfom.model.Lesson;
import com.example.lms_platfom.model.User;
import com.example.lms_platfom.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseService courseService;

    public Lesson create(UserDetails userDetails, LessonDto lessonDto){
        Course course = courseService.findById(lessonDto.getCourseId());

        if(!course.getTeacher().getUser().getEmail().equals(userDetails.getUsername()))
            throw new RuntimeException("You can not add lesson for this course");

        return lessonRepository.save(Lesson.builder()
                .title(lessonDto.getTitle())
                .description(lessonDto.getDescription())
                .content(lessonDto.getContent())
                .course(course)
                .build());

    }

    public List<Lesson> getLessonsOfCourse(Long courseId) {
        return lessonRepository.findByCourseCourseId(courseId);
    }

    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }
}
