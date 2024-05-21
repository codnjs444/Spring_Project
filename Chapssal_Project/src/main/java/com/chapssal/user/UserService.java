package com.chapssal.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chapssal.DataNotFoundException;

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
    
    public User getUser(String userid) {
		Optional<User> siteUser = this.userRepository.findByUserId(userid);
		if(siteUser.isPresent()) {
			return siteUser.get();
		} else {
			throw new DataNotFoundException("siteuser not found");
		}
		
	}

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
