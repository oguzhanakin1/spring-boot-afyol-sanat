package com.example.photographerApp.service;

import com.example.photographerApp.exception.MemberNotFoundException;
import com.example.photographerApp.model.Member;
import com.example.photographerApp.repository.MemberRepository;
import com.example.photographerApp.request.MemberCreateRequest;
import com.example.photographerApp.request.MemberUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService
{
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository)
    {
        this.memberRepository = memberRepository;
    }

    public Member createMember(MemberCreateRequest request)
    {
        Member member = new Member();
        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setMemberRole(request.getMemberRole());
        return memberRepository.save(member);
    }

    public Member updateMember(Long memberId, MemberUpdateRequest request)
    {
        Member memberToEdit = memberRepository.findById(memberId).orElseThrow();
        memberToEdit.setFirstName(request.getFirstName());
        memberToEdit.setLastName(request.getLastName());
        memberToEdit.setMemberRole(request.getMemberRole());

        return memberRepository.save(memberToEdit);
    }

    public void deleteOneMemberById(Long memberId)
    {
        memberRepository.deleteById(memberId);
    }
    public List<Member> getAll()
    {
        return memberRepository.findAll();
    }

    public Member findOneMemberById(Long memberId)
    {
        return memberRepository.findById(memberId)
                .orElseThrow(()->
                        new MemberNotFoundException("Member not found with id: "
                                + memberId));
    }
}
