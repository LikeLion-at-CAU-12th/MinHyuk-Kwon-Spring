package com.example.demo.repository;

import com.example.demo.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest // 이 친구 까먹지마!
public class MemberJpaRepositoryTest {
    @Autowired
    MemberJpaRepository memberJpaRepository;

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

        Member saveMember = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.findById(saveMember.getId()).orElse(null);

        // 잘 찾아왔는지
        Assertions.assertThat(findMember).isNotNull();
        // 저장한거랑 가져온거랑 같은지
        Assertions.assertThat(findMember.getId()).isEqualTo(saveMember.getId());
        Assertions.assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    @Transactional
    void findByUsername() {
        Member member1 = Member.builder()
                .username("memberA")
                .age(23)
                .email("test1234@naver.com")
                .build();
        Member member2 = Member.builder()
                .username("memberB")
                .age(24)
                .email("test1234@google.com")
                .build();
        Member member3 = Member.builder()
                .username("memberB")
                .age(25)
                .email("test1234@daum.com")
                .build();

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);

        List<Member> findMembers = memberJpaRepository.findByUsername(member2.getUsername());

        Assertions.assertThat(findMembers).hasSize(2);
        Assertions.assertThat(findMembers.contains(member2)).isTrue();
        Assertions.assertThat(findMembers.contains(member3)).isTrue();
    }

    @Test
    @Transactional
    void findAll() {
        Member member1 = Member.builder()
                .username("memberA")
                .age(23)
                .email("test1234@naver.com")
                .build();
        Member member2 = Member.builder()
                .username("memberB")
                .age(24)
                .email("test1234@google.com")
                .build();
        Member member3 = Member.builder()
                .username("memberB")
                .age(25)
                .email("test1234@daum.com")
                .build();

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        memberJpaRepository.save(member3);

        List<Member> findMembers = memberJpaRepository.findAll();

        Assertions.assertThat(findMembers).hasSize(3);
    }
}
