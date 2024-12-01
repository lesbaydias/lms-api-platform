package com.example.lms_platfom.model;


import com.example.lms_platfom.enums.AssignmentType;
import com.example.lms_platfom.enums.QuestionType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;

    @Column(nullable = false)
    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

//    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<StudentAssignment> studentAssignments;

    @NotNull(message = "Deadline is required")
    private LocalDateTime deadline;

    @Enumerated(EnumType.STRING)
    private AssignmentType assignmentType;
}
