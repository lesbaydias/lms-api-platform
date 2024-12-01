package com.example.lms_platfom.model;

import com.example.lms_platfom.enums.QuestionType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @ElementCollection
    @Column(name = "options")
    private List<String> options;

    private String correctAnswer;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;
}
