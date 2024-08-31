package com.example.meven.arpentage_api.controller;

import com.example.meven.arpentage_api.model.Member;
import com.example.meven.arpentage_api.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/member/{id}")
    ResponseEntity<?> getMemberById(@PathVariable("id") final int id, Model model) {
        try {
            Member member = memberService.getMemberById(id);
            return ResponseEntity.ok(member);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // TODO : only enable the connected user to delete his own account
    //Or protect it by an admin right
    @DeleteMapping("/member/{id}")
    ResponseEntity<?> deleteMember(@PathVariable("id") final int id) {
        try {
            memberService.deleteMemberById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/members")
    ResponseEntity<?> getAllMembers() {
        try {
            Iterable<Member> members = memberService.getAllMembers();
            return ResponseEntity.ok(members);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/member/{id}")
    ResponseEntity<?> updateMember(@PathVariable("id") final int id, @RequestBody final Member member) {
        try {
            Member memberToUpdate = memberService.getMemberById(id);
            if (member.getMail() != null) {
                memberToUpdate.setMail(member.getMail());
            }
            if (member.getPseudo() != null) {
                memberToUpdate.setPseudo(member.getPseudo());
            }
            memberService.saveMember(memberToUpdate);
            return ResponseEntity.ok(memberToUpdate);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/member")
    Member createMember(@RequestBody final Member member) {
        return memberService.saveMember(member);
    }


}
