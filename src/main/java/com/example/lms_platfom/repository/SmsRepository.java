package com.example.lms_platfom.repository;

import com.example.lms_platfom.model.Sms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsRepository extends JpaRepository<Sms, Long>  {
    Sms findSmsByUser_UserId(Long userId);
}