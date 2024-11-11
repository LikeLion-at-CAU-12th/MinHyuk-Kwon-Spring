package com.example.demo.repository;

import com.example.demo.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// 내부적으로 reflection
@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> { // (entity Class, PK type)
    // username으로 조회하는 queryMethod
    List<Member> findByUsername(String username);

    Member findOneByUsername(String username);

    Optional<Member> findByEmail(String email);

    // 툭정 나이 이상인 Member 페이징 조회
    Page<Member> findByAgeGreaterThanEqual(int age, Pageable pageable);

    // 이름이 특정 문자열로 시작하는 Member 페이징 조회
    Page<Member> findByUsernameStartingWith(String target, Pageable pageable);

    // 회원가입 로직
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}