package com.example.lms_platfom.model;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long basketId;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "basket_courses",
            joinColumns = @JoinColumn(name = "basket_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new CopyOnWriteArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;
}