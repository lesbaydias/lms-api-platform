package com.example.lms_platfom.model;

import com.example.lms_platfom.enums.AssignmentStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentAssignmentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    private String submission;

    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;

    private Double grade;

    private LocalDateTime submittedAt;
}
