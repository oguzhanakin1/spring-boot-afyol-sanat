package com.example.photographerApp.service;

import com.example.photographerApp.exception.MemberNotFoundException;
import com.example.photographerApp.model.Member;
import com.example.photographerApp.repository.MemberRepository;
import com.example.photographerApp.request.MemberCreateRequest;
import com.example.photographerApp.request.MemberUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class MemberServiceTest
{
    private MemberService memberService;

    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp()
    {
        memberRepository = mock(MemberRepository.class);
        memberService = new MemberService(memberRepository);
    }

    @Test
    void findOneMemberById_whenMemberIdExists_shouldReturnMember()
    {
        Member member = new Member(1L   ,"f_name","l_name","role");

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        Member result = memberService.findOneMemberById(1L);

        assertEquals(result,member);
    }

    @Test
    void findOneMemberById_whenMemberIdDoesNotExists_shouldThrowMemberNotFoundException()
    {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class,
                () -> memberService.findOneMemberById(1L));
    }

    @Test
    void findAllMember_whenAnyAmountOfMemberExists_shouldReturnMemberResponseList()
    {
        List<Member> members = new ArrayList<>();
        members.add(new Member());

        when(memberRepository.findAll()).thenReturn(members);

        List<Member> expected = memberService.getAll();

        assertEquals(expected,members);

        verify(memberRepository).findAll();
    }

    @Test
    void createMember_whenCreateMember_shouldReturnMember()
    {
        Member member = new Member(1L,"f_name","l_name","role");

        MemberCreateRequest request = new MemberCreateRequest();
        request.setFirstName("f_name");
        request.setLastName("l_name");
        request.setMemberRole("role");

        when(memberRepository.save(ArgumentMatchers.any(Member.class))).thenReturn(member);

        Member createdMember = memberService.createMember(request);

        assertEquals(createdMember,member);
    }

    @Test
    void updateMember_whenMemberExists_shouldReturnMember()
    {
        Member member = new Member(1L,"f_name","l_name","role");

        Member editedMember = new Member
                (1L,"new f_name","new l_name","new role");

        MemberUpdateRequest request = new MemberUpdateRequest();
        request.setFirstName("new f_name");
        request.setLastName("new l_name");
        request.setMemberRole("new role");

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberRepository.save(ArgumentMatchers.any(Member.class))).thenReturn(member);

        Member updatedMember = memberService.updateMember(1L, request);

        verify(memberRepository).findById(1L);
        verify(memberRepository).save(editedMember);

        assertEquals(updatedMember,editedMember);
    }

    @Test
    void updateMember_whenMemberDoesNotExists_shouldThrowMemberNotFoundException()
    {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class,
                () -> memberService.findOneMemberById(1L));
    }

    @Test
    void deleteMember_whenMemberExists_shouldDeleteUser()
    {
        Member member = new Member(1L,"f_name","l_name","role");

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        memberService.deleteOneMemberById(1L);

        verify(memberRepository).deleteById(1L);
    }

    @Test
    void deleteMember_whenMemberExists_shouldThrowMemberNotFoundException()
    {
        when(memberRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class,
                () -> memberService.findOneMemberById(1L));
    }
}