package com.example.demo.repository;

import com.example.demo.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;


    @Test
    @Transactional
    @Rollback(value = true)
    void save() {
        Member member = Member.builder()
                .username("memberA")
                .age(23)
                .email("test1234@naver.com")
                .build();

        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.findOne(saveId);

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    @Transactional
    void save2() {
        Member member = Member.builder()
                .username("memberA")
                .age(23)
                .email("test1234@naver.com")
                .build();

        Long saveId = memberRepository.save(member);

        em.flush();
        em.clear();

        Member findMember = memberRepository.findOne(saveId);

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    void findOne() {
    }

    @Test
    void findAll() {
    }

    @Test
    void findByUsername() {
    }
}