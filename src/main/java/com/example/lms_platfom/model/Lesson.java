package com.example.lms_platfom.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;

    @Column(nullable = false)
    private String title;
    private String description;
    private String content;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
