package com.spring.flashcard.Repository;

import com.spring.flashcard.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    User findByEmail(String email);
    User findByUserName(String username);
    User findByUserNameAndPassWord(String username, String password);
}
