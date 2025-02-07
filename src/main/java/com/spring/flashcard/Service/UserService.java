package com.spring.flashcard.Service;

import com.spring.flashcard.DTO.UserDTO;
import com.spring.flashcard.Entity.User;

public interface UserService {
    public void save(UserDTO userDTO);
    public boolean verifyAccount(String token);
    public boolean authenticate(String username, String rawpassword);
    public boolean verifyOtp(String email, String otp);
    public boolean resetPassword(String email, String newPassword);
    public boolean generateOtp(String email);
}
