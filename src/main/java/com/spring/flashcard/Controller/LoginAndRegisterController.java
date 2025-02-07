package com.spring.flashcard.Controller;

import com.spring.flashcard.DTO.UserDTO;
import com.spring.flashcard.Exception.UserException;
import com.spring.flashcard.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/auth")
public class LoginAndRegisterController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO){
        userService.save(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Đăng ký thành công");
    }
    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("token") String token){
        boolean isVerified = userService.verifyAccount(token);
        if(isVerified){
            return ResponseEntity.ok("Xác minh tài khoản thành công");
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã xác minh không hợp lệ hoặc hết hạn");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO){
        boolean isAuthenticated = userService.authenticate(userDTO.getUserName(), userDTO.getPassWord());
        if(isAuthenticated){
            return ResponseEntity.ok("Đăng nhập thành công");
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Tên tài khoản hoặc mật khẩu không đúng");
        }
    }
    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleUserException(UserException e){
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}
