package com.example.lms_platfom.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;

    @Column(nullable = false)
    private String title;

    @OneToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;
}
