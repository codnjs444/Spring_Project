
package com.example.demo.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.user.model.User;


public interface UserRepository extends JpaRepository<User, String> {
	 Optional<User> findByUserId(String userId);
}