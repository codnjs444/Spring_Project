package com.chapssal.user;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chapssal.school.School;

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
    

    public String getSchoolNameByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .map(User::getSchool)
                .map(School::getSchoolName)
                .orElse("학교 정보 없음");
    }
    
    public Integer getUserNumByUserId(String userId) {
        return userRepository.findUserNumByUserId(userId);
    }
    
    public String getUserNameByUserId(String userId) {
        return userRepository.findUserNameByUserId(userId)
                             .orElse("사용자"); // 사용자 이름이 없을 경우 "사용자"를 반환
    }
    
    public User updateUserName(String userId, String newUserName, String bio) {
        User user = userRepository.findByUserId(userId).orElse(new User());
        user.setUserName(newUserName);
        user.setBio(bio);
        return userRepository.save(user);
    }
    
    public String getUserBioByUserId(String userId) {
        return userRepository.findByUserId(userId)
                             .map(User::getBio)
                             .orElse("");
    }
    

}
