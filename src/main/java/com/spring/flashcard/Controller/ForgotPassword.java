package com.spring.flashcard.Controller;

import com.spring.flashcard.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class ForgotPassword {
    @Autowired
    private UserService userService;
    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestParam String email){
        boolean sendMessage = userService.generateOtp(email);
        if(sendMessage){
            return ResponseEntity.ok("Đã gửi otp qua email");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email không tồn tại");
    }
    @PostMapping("/verifyOtp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp){
        boolean isValid = userService.verifyOtp(email, otp);
        if(isValid){
            return ResponseEntity.ok("OTP hợp lệ");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP không hợp lệ");
        }
    }
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String newPassword){
        boolean isReset = userService.resetPassword(email, newPassword);
        if(isReset){
            return ResponseEntity.ok("Đổi mật khẩu thành công");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể đặt lại mật khẩu");
        }
    }
}
