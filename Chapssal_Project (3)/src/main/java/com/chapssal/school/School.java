package com.chapssal.school;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "School")
@Getter
@Setter
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer schoolNum;

    @Column(length = 255)
    private String schoolName;

    @Column(length = 255)
    private String postalCode;

    @Column(length = 255)
    private String schoolAddress;

    @Column(length = 255)
    private String schoolPhone;
    
    @Column(length = 255)
    private String schoolCode;

}