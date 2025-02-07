package com.spring.flashcard.Service.Impl;

import com.spring.flashcard.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendVerificationEmail(String to, String token) {
        String subject = "Xác nhận tài khoản của bạn";
        String confirmationUrl = "http://localhost:8080/api/auth/verify?token=" + token;
        String message = "Nhấn vào liên kết sau để xác minh tài khoản của bạn: " + confirmationUrl;

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }
}
