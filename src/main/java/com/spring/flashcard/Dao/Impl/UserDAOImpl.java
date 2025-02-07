package com.spring.flashcard.Dao.Impl;

import com.spring.flashcard.Dao.UserDAO;
import com.spring.flashcard.Entity.User;
import com.spring.flashcard.Exception.UserException;
import com.spring.flashcard.Repository.UserRepository;
import com.spring.flashcard.Service.EmailService;
import com.spring.flashcard.Token.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    private Map<String, String> verificationTokens = new HashMap<>();
    @Autowired
    private JavaMailSender javaMailSender;
    private Map<String, String> otpStorage = new HashMap<>();
    @Override
    public void save(User user) {
        if(userRepository.existsByUserName(user.getUserName())){
            throw new UserException("Username đã tồn tại", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new UserException("Email đã tồn tại", HttpStatus.BAD_REQUEST);
        }
//        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        userRepository.save(user);
        // Tạo token
        String token = TokenGenerator.generateToken();
        verificationTokens.put(token, user.getEmail());

        // Gửi email xác minh
        emailService.sendVerificationEmail(user.getEmail(), token);
    }

    @Override
    public boolean verifyAccount(String token) {
        String email = verificationTokens.get(token);
        if (email == null) {
            return false; // Token không hợp lệ
        }

        // Cập nhật trạng thái tài khoản
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setVerified(1);
            userRepository.save(user);
            verificationTokens.remove(token);
            return true;
        }
        return false;
    }

    @Override
    public boolean authenticate(String username, String rawpassword) {
        User user = userRepository.findByUserName(username);
        if(user == null){
            return false;
        }
        System.out.println("Raw: " + rawpassword);
        System.out.println("Hashed: " + user.getPassWord());
        System.out.println("Matches: " + passwordEncoder.matches(rawpassword, user.getPassWord()));
        System.out.println(passwordEncoder.getClass());
        return passwordEncoder.matches(rawpassword, user.getPassWord());
    }

    @Override
    public boolean generateOtp(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            return false;
        }
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(email, otp);
        sendOtpEmail(email, otp);
        return true;
    }

    @Override
    public boolean verifyOtp(String email, String otp) {
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(otp);
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            return false;
        }
        user.setPassWord(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        otpStorage.remove(email);
        return true;
    }

    private void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Mã OTP đặt lại mật khẩu");
        message.setText("Mã OTP của bạn là: " + otp);
        javaMailSender.send(message);
    }
}
