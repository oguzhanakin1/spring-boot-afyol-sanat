package com.example.photographerApp.service;

import com.example.photographerApp.model.Member;
import com.example.photographerApp.repository.MemberRepository;
import com.example.photographerApp.request.MemberRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService implements IMemberService
{
    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository)
    {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member createMember(MemberRequest request)
    {
        Member member = new Member();
        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setMemberRole(request.getMemberRole());
        return memberRepository.save(member);
    }

    @Override
    public Member updateMember(Long memberId, MemberRequest request)
    {
        Member memberToEdit = memberRepository.findById(memberId).orElseThrow();
        memberToEdit.setFirstName(request.getFirstName());
        memberToEdit.setLastName(request.getLastName());
        memberToEdit.setMemberRole(request.getMemberRole());

        return memberRepository.save(memberToEdit);
    }

    @Override
    public void deleteOneMemberById(Long memberId)
    {
        memberRepository.deleteById(memberId);
    }
    @Override
    public List<Member> getAll()
    {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findOneMemberById(Long memberId)
    {
        return memberRepository.findById(memberId);
    }
}
