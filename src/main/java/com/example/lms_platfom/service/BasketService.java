package com.example.lms_platfom.service;

import com.example.lms_platfom.model.Basket;
import com.example.lms_platfom.model.Course;
import com.example.lms_platfom.model.User;
import com.example.lms_platfom.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;
    private final UserService userService;
    private final CourseService courseService;

    public void create(User user) {
        Basket basket = Basket.builder()
                .courses(new CopyOnWriteArrayList<>())
                .user(user)
                .build();
        basketRepository.save(basket);
    }

    public Basket findAllOwnCourses(UserDetails userDetails){
        User user = userService.findByEmail(userDetails.getUsername());
        return basketRepository.findByUserUserId(user.getUserId());
    }

    public Basket addCourseToBasket(Long courseId, UserDetails userDetails){
        User user = userService.findByEmail(userDetails.getUsername());
        Basket basket = basketRepository.findByUserUserId(user.getUserId());
        Course course = courseService.findById(courseId);

        List<Course> courseList = basket.getCourses();
        courseList.add(course);
        basket.setCourses(courseList);
        basketRepository.save(basket);
        return basket;
    }
}
