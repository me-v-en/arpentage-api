package com.example.meven.arpentage_api.service;

import com.example.meven.arpentage_api.model.Member;
import com.example.meven.arpentage_api.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public boolean isMemberExisting(int id) {
        Optional<Member> member = memberRepository.findById(id);

        return member.isPresent();
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public Member getMemberById(int id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isEmpty()) {
            throw new IllegalArgumentException("Member with id " + id + " not found");
        }
        return member.get();
    }

    public Iterable<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public void deleteMemberById(int id) {
        if(!isMemberExisting(id)){
            throw new IllegalArgumentException("Member to delete not found");
        }
        memberRepository.deleteById(id);
    }
}
