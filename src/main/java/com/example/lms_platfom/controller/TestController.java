package com.example.lms_platfom.controller;

import com.example.lms_platfom.dto.TestDto;
import com.example.lms_platfom.model.Test;
import com.example.lms_platfom.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    private final TestService testService;

    @PostMapping("/create")
    public Test create(@RequestBody TestDto testDto, @AuthenticationPrincipal UserDetails userDetails) {
        return testService.save(testDto, userDetails);
    }

    @PostMapping("/{testId}/addUser/{userId}")
    public ResponseEntity<String> addUserToTest(@AuthenticationPrincipal UserDetails userDetails,
                                                @PathVariable Long testId,
                                                @PathVariable Long userId
    ) {
        try {
            testService.addUserToTest(userId, testId, userDetails);
            return ResponseEntity.ok("User added to test successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding user to test: " + e.getMessage());
        }
    }
}
