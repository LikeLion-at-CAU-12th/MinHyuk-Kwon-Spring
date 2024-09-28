package com.example.demo.service;

import com.example.demo.domain.Member;
import com.example.demo.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;

    public Page<Member> getMemberByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page,size, Sort.by("username").ascending());
        return memberJpaRepository.findAll(pageable);
    }

    public void printMemberByPage(int page, int size) {
        Page<Member> memberPage = getMemberByPage(page, size);
        List<Member> members = memberPage.getContent();

        for (Member member : members) {
            System.out.println("Id :"+member.getId() + ", Username : "+member.getUsername());
        }
    }

}
