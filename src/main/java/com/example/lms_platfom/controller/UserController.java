package com.example.lms_platfom.controller;

import com.example.lms_platfom.dto.UserUpdateDto;
import com.example.lms_platfom.model.User;
import com.example.lms_platfom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public User getAllInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.findByEmail(userDetails.getUsername());
    }

    @PostMapping("/upload-image")
    public String uploadImage(@RequestParam("file") MultipartFile file,
                              @AuthenticationPrincipal UserDetails userDetails) {
       return userService.uploadFile(file, userDetails);
    }

    @PutMapping("/update")
    public User update(@AuthenticationPrincipal UserDetails userDetails,
                       @RequestBody UserUpdateDto userUpdateDto) {
        return userService.update(userUpdateDto, userDetails);
    }

    @PutMapping("/update-image")
    public String updateImage(@RequestParam("file") MultipartFile file,
                            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return userService.updateImage(file, userDetails);
    }

    @DeleteMapping("/delete-iamge")
    public String deleteImage(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.deleteImage(userDetails);
    }
}
