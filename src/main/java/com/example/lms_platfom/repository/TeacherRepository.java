package com.example.lms_platfom.repository;

import com.example.lms_platfom.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT t FROM Teacher t WHERE t.user.userId = :userId")
    Optional<Teacher> findByUserUserId(@Param("userId") Long userId);

}