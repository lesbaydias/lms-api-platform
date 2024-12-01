package com.example.lms_platfom.repository;

import com.example.lms_platfom.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket, Long> {
    Basket findByUserUserId(Long userId);
}
