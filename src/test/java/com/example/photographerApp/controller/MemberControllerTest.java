package com.example.photographerApp.controller;

import com.example.photographerApp.model.Member;
import com.example.photographerApp.repository.MemberRepository;
import com.example.photographerApp.request.MemberCreateRequest;
import com.example.photographerApp.request.MemberUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "server-port = 0"
})
@RunWith(SpringRunner.class)
@DirtiesContext
class MemberControllerTest
{
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void setup()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        memberRepository.deleteAll();
    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testCreateMemberWithAValidAuthority_ShouldCreateMemberAndReturnMember()
            throws Exception
    {
        MemberCreateRequest request = new MemberCreateRequest();
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setMemberRole("member");

        this.mockMvc
                .perform(post("/api/members/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")))
                .andExpect(jsonPath("$.memberRole", is("member")));
    }

    @Test//invalid authority
    @WithMockUser(authorities = "manager")
    public void testCreateMemberWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {
        MemberCreateRequest request = new MemberCreateRequest();
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setMemberRole("member");

        this.mockMvc
                .perform(post("/api/members/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testUpdateMemberWithAValidAuthority_ShouldUpdateMemberAndReturnMember()
            throws Exception
    {
        Member member = memberRepository.save(new Member(1L, "firstName", "lastName", "member"));
        MemberUpdateRequest request = new MemberUpdateRequest();
        request.setFirstName("new firstName");
        request.setLastName("new lastName");
        request.setMemberRole("new member");

        this.mockMvc
                .perform(put("/api/members/update/{memberId}", member.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("new firstName")))
                .andExpect(jsonPath("$.lastName", is("new lastName")))
                .andExpect(jsonPath("$.memberRole", is("new member")));
    }

    @Test//invalid authority
    @WithMockUser(authorities = "manager")
    public void testUpdateMemberWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {

        Member member = memberRepository.save(new Member(1L, "firstName", "lastName", "member"));
        MemberUpdateRequest request = new MemberUpdateRequest();
        request.setFirstName("new firstName");
        request.setLastName("new lastName");
        request.setMemberRole("new member");

        this.mockMvc
                .perform(put("/api/members/update/{memberId}", member.getId())
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testDeleteMemberWithAValidAuthority_ShouldDeleteMember()
            throws Exception
    {
        Member member = memberRepository.save(new Member(1L, "firstName", "lastName", "member"));

        this.mockMvc
                .perform(delete("/api/members/delete/{memberId}", member.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test//valid authority
    @WithMockUser(authorities = "manager")
    public void testDeleteMemberWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {
        Member member = memberRepository.save(new Member(1L, "firstName", "lastName", "member"));

        this.mockMvc
                .perform(delete("/api/members/delete/{memberId}", member.getId()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test//valid authority
    public void testGetAllMembersWhenRequested_ShouldReturnListOfMemberResponses()
            throws Exception
    {
        List<Member> members = new ArrayList<>();
        members.add(new Member(1L, "firstName", "lastName", "member"));
        members.add(new Member(1L, "new firstName", "new lastName", "new member"));
        memberRepository.saveAll(members);

        this.mockMvc.perform(get("/api/members/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test//valid id
    public void testGetMemberWithAValidId_ShouldReturnMemberResponse()
            throws Exception
    {
        Member member = memberRepository.save(new Member(1L, "firstName", "lastName", "member"));

        this.mockMvc.perform(get("/api/members/get/{memberId}", member.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")))
                .andExpect(jsonPath("$.memberRole", is("member")));
    }

    @Test//invalid id
    public void testGetMemberWithAnInvalidId_ShouldReturnHttpStatus_NotFound()
            throws Exception
    {
        this.mockMvc.perform(get("/api/members/get/{memberId}", 0L))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}