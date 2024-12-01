package com.example.lms_platfom.service;

import com.example.lms_platfom.config.aws.AwsService;
import com.example.lms_platfom.dto.UserUpdateDto;
import com.example.lms_platfom.model.User;
import com.example.lms_platfom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AwsService awsService;


    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findUsersByEmail(email);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public String uploadFile(MultipartFile file, UserDetails userDetails) {
        String imageUrl = awsService.uploadFile(file);
        User user = userRepository.findUsersByEmail(userDetails.getUsername());
        user.setImageUrl(imageUrl);
        userRepository.save(user);
        return imageUrl;
    }

    public String updateImage(MultipartFile file, UserDetails userDetails) {
        User user = userRepository.findUsersByEmail(userDetails.getUsername());

        if(user.getImageUrl().length() > 1)
            user.setImageUrl(awsService.updateFile(user.getImageUrl(), file));


        return user.getImageUrl();
    }


    public User update(UserUpdateDto userUpdateDto, UserDetails userDetails) {
        User user = userRepository.findUsersByEmail(userDetails.getUsername());
        user.setCity(userUpdateDto.getCity());
        user.setLastname(userUpdateDto.getLastname());
        user.setFirstname(userUpdateDto.getFirstname());
        user.setBirthdayDate(user.getBirthdayDate());

        return userRepository.save(user);
    }


    public String deleteImage(UserDetails userDetails){
        User user = userRepository.findUsersByEmail(userDetails.getUsername());
        if(user.getImageUrl().length() <= 1)
            throw new RuntimeException("You dont have any image");

        awsService.deleteFile(user.getImageUrl());
        user.setImageUrl("");

        userRepository.save(user);
        return "Successfully deleted image";

    }
}
