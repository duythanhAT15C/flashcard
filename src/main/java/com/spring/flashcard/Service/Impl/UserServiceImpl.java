package com.spring.flashcard.Service.Impl;

import com.spring.flashcard.Convert.ConvertToEntity;
import com.spring.flashcard.DTO.UserDTO;
import com.spring.flashcard.Dao.UserDAO;
import com.spring.flashcard.Entity.User;
import com.spring.flashcard.Service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public void save(UserDTO userDTO) {
        User user = ConvertToEntity.convertUser(userDTO);
        user.setPassWord(passwordEncoder.encode(userDTO.getPassWord()));
        userDAO.save(user);
    }

    @Override
    @Transactional
    public boolean verifyAccount(String token) {
        return userDAO.verifyAccount(token);
    }

    @Override
    @Transactional
    public boolean authenticate(String username, String rawpassword) {
        return userDAO.authenticate(username, rawpassword);
    }

    @Override
    @Transactional
    public boolean verifyOtp(String email, String otp) {
        return userDAO.verifyOtp(email, otp);
    }

    @Override
    @Transactional
    public boolean resetPassword(String email, String newPassword) {
        return userDAO.resetPassword(email, newPassword);
    }

    @Override
    @Transactional
    public boolean generateOtp(String email) {
        return userDAO.generateOtp(email);
    }
}
