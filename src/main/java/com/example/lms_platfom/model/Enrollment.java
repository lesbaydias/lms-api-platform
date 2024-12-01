package com.example.lms_platfom.model;

import com.example.lms_platfom.enums.CourseStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;


    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
