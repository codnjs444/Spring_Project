package com.example.demo.user.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.user.model.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(String userId, String username, String email, String password) {
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreateDate(LocalDateTime.now());
        user.setLastUpdate(LocalDateTime.now().toString());

        System.out.println("User ID: " + userId); // 로그 추가

        this.userRepository.save(user);
        return user;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
