package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.dto.request.JoinRequest;
import com.example.demo.repository.MemberJpaRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;
    private final MemberRepository memberRepository;

    // Page단위로 Member를 반환하는 메서드
    public Page<Member> getMemberByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("username").ascending());
        return memberJpaRepository.findAll(pageable);
    }

    // 특정 나이 이상인 경우만 조회하고, 이름을 기준으로 오름차순 정렬된 페이징 결과 반환
    public Page<Member> getMembersByAgeGreaterThanEqual(int page, int size, int age) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return memberJpaRepository.findByAgeGreaterThanEqual(age, pageable);
    }

    // 이름이 주어진 값으로 시작하는 경우만 필터링하여 페이징 결과 반환
    public Page<Member> getMembersByUsernameStartingWith(int page, int size, String target) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("username").ascending());
        return memberJpaRepository.findByUsernameStartingWith(target, pageable);
    }

    // 특정 Page의 Member를 출력하는 메서드
    public void printMemberByPage(int page, int size) {
        Page<Member> memberPage = getMemberByPage(page, size);
        List<Member> members = memberPage.getContent();

        for (Member member : members) {
            System.out.println("Id :"+member.getId() + ", Username : "+member.getUsername());
        }
    }

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(JoinRequest joinRequest) {
        if (memberJpaRepository.existsByUsername(joinRequest.getUsername())) {
            return;
        }

        Member member = Member.builder()
                .username(joinRequest.getUsername())
                .password(bCryptPasswordEncoder.encode(joinRequest.getPassword()))
                .build();

        memberJpaRepository.save(member);
    }


}
