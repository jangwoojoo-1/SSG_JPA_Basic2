package com.ssg.lp.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String loginId;

    @Column(length = 100, nullable = false)
    private String loginPw;

    @Column(length = 100, nullable = false)
    private String address;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created;

    public Member(){}
    public Member(String name, String loginId, String loginPw, String address) {
        this.name = name;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.address = address;
    }

    public Member(Integer id, String name, String loginId, String loginPw, String address) {
        this.id = id;
        this.name = name;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.address = address;
    }
}
