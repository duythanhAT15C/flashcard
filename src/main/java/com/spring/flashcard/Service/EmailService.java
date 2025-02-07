package com.spring.flashcard.Service;

public interface EmailService {
    public void sendVerificationEmail(String to, String token);
}
