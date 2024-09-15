package com.example.demo.domain;

import com.example.demo.repository.MemberRepository;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String username;
    private String email;
    private int age;

    @Builder
    public Member(String username, String email, int age) {
        this.username = username;
        this.email = email;
        this.age = age;
    }
}
