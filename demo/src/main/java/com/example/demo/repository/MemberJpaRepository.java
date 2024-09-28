package com.example.demo.repository;

import com.example.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// 내부적으로 reflection
@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> { // (entity Class, PK type)
    // username으로 조회하는 queryMethod
    List<Member> findByUsername(String username);
}