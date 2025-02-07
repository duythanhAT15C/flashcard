package com.spring.flashcard.Dao;

import com.spring.flashcard.Entity.User;

public interface UserDAO {
    public void save(User user);
    public boolean verifyAccount(String token);
    public boolean authenticate(String username, String rawpassword);
    public boolean generateOtp(String email);
    public boolean verifyOtp(String email, String otp);
    public boolean resetPassword(String email, String newPassword);
}
