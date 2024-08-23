package com.example.meven.arpentage_api.service;

import com.example.meven.arpentage_api.model.Member;
import com.example.meven.arpentage_api.repository.MemberRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> getMemberById(long id) {
        return memberRepository.findById(id);
    }

    public Iterable<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public void deleteMemberById(long id) {
        memberRepository.deleteById(id);
    }
}
