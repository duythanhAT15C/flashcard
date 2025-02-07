package com.spring.flashcard.Convert;

import com.spring.flashcard.DTO.UserDTO;
import com.spring.flashcard.Entity.User;

public class ConvertToEntity {
    public static User convertUser(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setUserName(userDTO.getUserName());
//        user.setPassWord(userDTO.getPassWord());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setDateCreate(userDTO.getDateCreate());
        user.setVerified(0);
        return user;
    }
}
