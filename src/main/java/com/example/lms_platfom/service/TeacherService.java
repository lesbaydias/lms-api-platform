package com.example.lms_platfom.service;

import com.example.lms_platfom.dto.UpdateTeacherDto;
import com.example.lms_platfom.model.Teacher;
import com.example.lms_platfom.model.User;
import com.example.lms_platfom.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.Update;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserService userService;

    public void create(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    public Teacher getByUserId(Long userId) {
        return teacherRepository.findByUserUserId(userId).orElse(null);
    }

    public Teacher getTeacher(UserDetails userDetails){
        User user = userService.findByEmail(userDetails.getUsername());

        return teacherRepository.findByUserUserId(user.getUserId()).orElseThrow(() ->
                new RuntimeException("User Not Found"));
    }

    public Teacher update(UserDetails userDetails, UpdateTeacherDto updateTeacherDto) {
        User user = userService.findByEmail(userDetails.getUsername());
        Teacher teacher = teacherRepository.findByUserUserId(user.getUserId()).orElseThrow(() ->
                new RuntimeException("User Not Found"));

        teacher.setPosition(updateTeacherDto.getPosition());
        teacher.setWorkExperience(updateTeacherDto.getWorkExperience());

        return teacherRepository.save(teacher);
    }



}
