package com.chapssal.user.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chapssal.user.model.User;
import com.chapssal.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(String userId, String password, LocalDateTime createDate, LocalDateTime lastUpdate, LocalDateTime lastLogin) {
        User user = new User();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreateDate(LocalDateTime.now());
        user.setLastUpdate(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
//        user.setAuthority(authority);

        userRepository.save(user);
        return user;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
