package com.chapssal.user;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
        user.setCreateDate(createDate);
        user.setLastUpdate(lastUpdate);
        user.setLastLogin(lastLogin);

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
}
