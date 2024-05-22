package com.chapssal.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(String userId, String password, Integer schoolNum, LocalDateTime createDate, LocalDateTime lastUpdate, LocalDateTime lastLogin) {
        Optional<User> existingUser = userRepository.findByUserId(userId);
        if (existingUser.isPresent()) {
            throw new DuplicateUserIdException("이미 존재하는 아이디입니다.");
        }

        User user = new User();
        user.setUserId(userId);
        user.setPassword(passwordEncoder.encode(password));
        user.setSchool(schoolNum);
        user.setCreateDate(createDate);
        user.setLastUpdate(lastUpdate);
        user.setLastLogin(lastLogin);
        user.setVote(0); // 기본값 설정

        userRepository.save(user);
        return user;
    }

    public User createSocialUser(String userId, String userName) {
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setCreateDate(LocalDateTime.now());

        userRepository.save(user);
        return user;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
