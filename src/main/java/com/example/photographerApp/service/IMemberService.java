package com.example.photographerApp.service;

import com.example.photographerApp.model.Member;
import com.example.photographerApp.request.MemberRequest;

import java.util.List;
import java.util.Optional;

public interface IMemberService
{
    Member createMember(MemberRequest request);

    Member updateMember(Long memberId, MemberRequest request);

    void deleteOneMemberById(Long memberId);

    List<Member> getAll();

    Optional<Member> findOneMemberById(Long memberId);
}
