package com.example.demo.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "User")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userNum", nullable = false)
    private Integer userNum;  // 유저 번호

    @Column(name = "userId", nullable = false)
    private String userId;  // 아이디

    @Column(name = "userName", nullable = true)
    private String userName;  // 사용자 이름

    @Column(name = "userNickName", nullable = true)
    private String userNickName;  // 사용자 닉네임

    @Column(name = "password", nullable = false)
    private String password;  // 비밀번호

    @Column(name = "profilePictureUrl", nullable = true)
    private String profilePictureUrl;  // 프로필 사진 URL

    @Column(name = "schoolNum", nullable = true)
    private Integer schoolNum;  // 소속 초등학교 번호

    @Column(name = "createDate", nullable = false)
    private LocalDateTime createDate;  // 계정 생성 일자

    @Column(name = "lastUpdate", nullable = true)
    private LocalDateTime lastUpdate;  // 마지막 업데이트

    @Column(name = "lastLogin", nullable = true)
    private LocalDateTime lastLogin;  // 마지막 로그인 시각

    @Column(name = "phoneNum", nullable = true)
    private Integer phoneNum;  // 휴대폰 번호

    @Column(name = "job", nullable = true)
    private String job;  // 직업

    @Column(name = "authority", nullable = true)
    private Integer authority;  // 권한 레벨
}
