package com.example.demo.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @Column(name = "user_id", nullable = false)
    private String userId;  // 사용자 아이디 (기본 키)

    @Column(name = "username", nullable = false)
    private String username;  // 사용자 이름

    @Column(name = "email", nullable = false)
    private String email;  // 이메일 주소

    @Column(name = "password", nullable = false)
    private String password;  // 비밀번호

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;  // 프로필 사진 URL

    @Column(name = "school_name")
    private String schoolName;  // 소속 초등학교

    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;  // 계정 생성 일자

    @Column(name = "last_update")
    private String lastUpdate;  // 마지막 업데이트

    @Column(name = "last_login")
    private LocalDateTime lastLogin;  // 마지막 로그인 시각
}
