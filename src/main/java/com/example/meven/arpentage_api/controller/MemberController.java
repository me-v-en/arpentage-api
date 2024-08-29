package com.example.meven.arpentage_api.controller;

import com.example.meven.arpentage_api.model.Member;
import com.example.meven.arpentage_api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/member/{id}")
    Optional<Member> getMemberById(@PathVariable("id") final int id, Model model) {
        return memberService.getMemberById(id);
    }

    // TODO : only enable the connected user to delete his own account
    //Or protect it by an admin right
    @DeleteMapping("/member/{id}")
    void deleteMember(@PathVariable("id") final int id) {
        memberService.deleteMemberById(id);
    }

    @GetMapping("/members")
    Iterable<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @PutMapping("/member/{id}")
    Member updateMember(@PathVariable("id") final int id, @RequestBody final Member member) {
        Optional<Member> m = memberService.getMemberById(id);
        // If member exists, update his properties
        if (m.isPresent()) {
            Member memberToUpdate = m.get();
            if (member.getMail() != null) {
                memberToUpdate.setMail(member.getMail());
            }
            if (member.getPseudo() != null) {
                memberToUpdate.setPseudo(member.getPseudo());
            }
            memberService.saveMember(memberToUpdate);
            return memberToUpdate;
            // If not, return null
            // TODO : Handle with exceptions ?
        } else return null;
    }

    @PostMapping("/member")
    Member createMember(@RequestBody final Member member) {
        return memberService.saveMember(member);
    }


}
