package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberJpaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private final Random random = new Random();

    @BeforeEach
    public void setUp() {
        IntStream.range(0, 100).forEach(i -> {
            String username = "user" + random.nextInt(10000);
            String email = "user" + random.nextInt(10000) + "@example.com";
            int age = random.nextInt(60 - 18 + 1) + 18;
            Member member = Member.builder()
                    .username(username)
                    .email(email)
                    .age(age)
                    .build();
            memberJpaRepository.save(member);
        });
    }

    @Test
    @Transactional
    public void testPrintMemberByPage() {
        memberService.printMemberByPage(0, 10); // 첫페이지의 10개 회원 정보 출력, size  값을 바꿔보면서 테스트!
    }

    @Test
    @Transactional
    public void tsetGetMembersByUsernameStartingWith() {
        Page<Member> result = memberService.getMembersByUsernameStartingWith(0, 10, "user1");

        // 'user1'로 시작하는지 확인
        List<Member> members = result.getContent();
        for (Member member : members) {
            Assertions.assertTrue(member.getUsername().startsWith("user1"));
        }

        System.out.println("Member의 이름이 'user1'로 시작하는 수 : " + members.size());

    }

    @Test
    @Transactional
    public void testGetMembersByAgeGreaterThanEqual() {
        Page<Member> result = memberService.getMembersByAgeGreaterThanEqual(0, 10, 20);

        // 나이가 20 이상인지 확인
        List<Member> members = result.getContent();
        for (Member member : members) {
            Assertions.assertTrue(member.getAge() >= 20);
        }

        System.out.println("20세 이상인 Member의 수 : " + members.size());
    }
}
