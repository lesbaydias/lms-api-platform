package com.example.lms_platfom.service;

import com.example.lms_platfom.config.aws.AwsService;
import com.example.lms_platfom.dto.LoginDto;
import com.example.lms_platfom.dto.RegisterDto;
import com.example.lms_platfom.enums.Role;
import com.example.lms_platfom.model.Basket;
import com.example.lms_platfom.model.Teacher;
import com.example.lms_platfom.model.User;
import com.example.lms_platfom.repository.AuthenticationRepository;
import com.example.lms_platfom.token.JwtParser;
import com.example.lms_platfom.token.dto.JwtAuthenticationResponseDto;
import com.example.lms_platfom.token.dto.RefreshTokenRequestDto;
import com.example.lms_platfom.token.repository.JwtRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationRepository {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtRepository JwtRepository;
    private final JwtParser jwtParser;
    private final UserService userService;
    private final TeacherService teacherService;
    private final BasketService basketService;

    @Override
    @Transactional
    public User register(RegisterDto registerDto) {
        User user = User.builder()
                .firstname(registerDto.getFirstname())
                .lastname(registerDto.getLastname())
                .phoneNumber(registerDto.getPhoneNumber())
                .roles(registerDto.getRoles())
                .city(registerDto.getCity())
                .birthdayDate(registerDto.getBirthdayDate())
                .imageUrl("")
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .email(registerDto.getEmail())
                .build();

        user = userService.save(user);
        if(registerDto.getRoles().name().equals(Role.TEACHER.name())){
            Teacher teacher = Teacher.builder()
                    .position(null)
                    .workExperience(null)
                    .user(user)
                    .build();
            teacherService.create(teacher);
        }

        basketService.create(user);
        return user;
    }



    @Override
    public JwtAuthenticationResponseDto login(LoginDto loginDto){
        UserDetails user = userService.findByEmail(loginDto.getEmail());

        if(user == null) throw new RuntimeException("User not found");

        if(!passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
            throw new RuntimeException("Wrong password");

        String jwt = JwtRepository.generateToken(user);
        String refreshToken = JwtRepository.generateRefreshToken(new HashMap<>(), user);

        authenticateUser(loginDto.getEmail(), loginDto.getPassword());

        return JwtAuthenticationResponseDto.builder()
                .timestamp(new Date())
                .username(user.getUsername())
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .build();
    }



    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }


    public JwtAuthenticationResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequest) {
        String email;
        try {
            email = jwtParser.extractUsername(refreshTokenRequest.getRefreshToken());
        }
        catch (SignatureException | ExpiredJwtException | MalformedJwtException ex){
            throw new RuntimeException("Invalid refresh token");
        }

        User user = userService.findByEmail(email);
        return generateResponse(user, refreshTokenRequest.getRefreshToken());
    }




    private JwtAuthenticationResponseDto generateResponse(UserDetails user, String refreshToken) {
        String jwt = JwtRepository.generateToken(user);

        return JwtAuthenticationResponseDto.builder()
                .timestamp(new Date())
                .username(user.getUsername())
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .build();
    }


}