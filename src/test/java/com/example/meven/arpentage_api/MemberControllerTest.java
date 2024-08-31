package com.example.meven.arpentage_api;

import com.example.meven.arpentage_api.model.Member;
import com.example.meven.arpentage_api.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;

    private Member testMember;

    @BeforeEach
    public void setUp() {
        Member m = new Member();
        m.setPseudo("test");
        m.setMail("test@test.com");

        testMember = memberService.saveMember(m);
    }

    @AfterEach
    public void tearDown() {
        if (memberService.isMemberExisting(testMember.getId())) {
            memberService.deleteMemberById(testMember.getId());
        }
    }

    @Test
    public void getMemberTest() throws Exception {
        mockMvc.perform(get("/member/" + testMember.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pseudo").value("test"));
    }

    @Test
    public void getAllMembersTest() throws Exception {
        mockMvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[-1:].pseudo").value("test"));
        // [-1:].pseudo : get the pseudo of the last inserted member (testMember)

    }

    @Test
    public void createMemberTest() throws Exception {
        Member m = new Member();
        m.setPseudo("createdTest");
        m.setMail("test@test.com");

        String payload = objectMapper.writeValueAsString(m);

        mockMvc.perform(post("/member")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pseudo").value("createdTest"));
    }

    @Test
    public void updateMemberTest() throws Exception {
        testMember.setPseudo("updatedTest");

        String payload = objectMapper.writeValueAsString(testMember);

        mockMvc.perform(put("/member/" + testMember.getId())
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pseudo").value("updatedTest"));
    }

    @Test
    public void deleteMemberTest() throws Exception {
        int testMemberId = testMember.getId();
        mockMvc.perform(delete("/member/" + testMemberId))
                .andExpect(status().isOk());

        assertFalse(memberService.isMemberExisting(testMemberId));
    }

}
