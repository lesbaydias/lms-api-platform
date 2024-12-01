package com.example.lms_platfom.service;

import com.example.lms_platfom.dto.PasswordRecoveryRequestEmail;
import com.example.lms_platfom.dto.PasswordRecoveryRequestNumber;
import com.example.lms_platfom.dto.ResetPasswordRequest;
import com.example.lms_platfom.dto.VerifyCodeRequest;
import com.example.lms_platfom.model.Sms;
import com.example.lms_platfom.model.User;
import com.example.lms_platfom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordRecoveryService {

    private final SmsService smsService;
    private final UserRepository userService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Transactional
    public void sendRecoveryCode(PasswordRecoveryRequestNumber request) {
        User user = userService.findUsersByPhoneNumber(request.getPhoneNumber());
        String recoveryCode = checkIfUserExists(user);
        sendSmsNotification(request.getPhoneNumber(), recoveryCode);
    }
    @Transactional
    public void sendRecoveryCodeEmail(PasswordRecoveryRequestEmail request) {
        User user = userService.findUsersByEmail(request.getEmail());
        String recoveryCode = checkIfUserExists(user);

        sendResetEmail(request.getEmail(), recoveryCode);
    }

    private String checkIfUserExists(User user) {
        if(user == null) throw new RuntimeException("User not found");

        Sms sms = smsService.findSmsByUser_UserId(user.getUserId());
        if (sms != null)
            smsService.delete(sms);

        String recoveryCode = generateRecoveryCode();

        sms = createSmsEntry(user, recoveryCode);
        smsService.save(sms);
        return recoveryCode;
    }


    private Sms createSmsEntry(User user, String code) {
        return Sms.builder()
                .code(code)
                .user(user)
                .build();
    }


    private void sendSmsNotification(String phoneNumber, String code) {
        String message = "Hi, "+phoneNumber+"\n\nPlease enter the following verification code to access your LMS Account:" + code;
        smsService.sendSms(phoneNumber, message);
    }


    public void verifyCode(VerifyCodeRequest request) {
        User user = userService.findUsersByPhoneNumber(request.getPhoneNumber());
        Sms sms = smsService.findSmsByUser_UserId(user.getUserId());
        if (sms == null)
            throw new RuntimeException("User not found");

        if(!sms.getCode().equalsIgnoreCase(request.getCode()))
            throw new RuntimeException("Incorrect verification code");
    }




    public void resetPassword(ResetPasswordRequest request) {
        User user = userService.findUsersByPhoneNumber(request.getPhoneNumber());
        if(user == null) throw new RuntimeException("User not found");

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.save(user);
    }

    private String generateRecoveryCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }


    private void sendResetEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset");
        message.setText("Hi, "+email+"\n\nPlease enter the following verification code to access your LMS Account:\n\n"
                + code);
        javaMailSender.send(message);
    }
}