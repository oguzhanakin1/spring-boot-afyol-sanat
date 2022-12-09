package com.example.photographerApp.controller;

import com.example.photographerApp.model.Member;
import com.example.photographerApp.request.MemberCreateRequest;
import com.example.photographerApp.request.MemberUpdateRequest;
import com.example.photographerApp.response.MemberResponse;
import com.example.photographerApp.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/members")
public class MemberController
{
    private final MemberService memberService;

    public MemberController(MemberService memberService)
    {
        this.memberService = memberService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MemberResponse>> findAll()
    {
        return ResponseEntity.ok(memberService.getAll().stream().map(
                member -> new MemberResponse(member)).collect(Collectors.toList()));
    }

    @GetMapping("get/{memberId}")
    public ResponseEntity<?> findOneMemberById(@PathVariable Long memberId)
    {
        Member member = memberService.findOneMemberById(memberId);
        return ResponseEntity.ok(new MemberResponse(member));
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public ResponseEntity<?> createMember(@RequestBody MemberCreateRequest request)
    {
        return new ResponseEntity<>(memberService.createMember(request), HttpStatus.CREATED);
    }

    @PutMapping("update/{memberId}")
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public ResponseEntity<?> updateMember(@PathVariable Long memberId,
                                         @RequestBody MemberUpdateRequest request)
    {
        return ResponseEntity.ok(memberService.updateMember(memberId, request));
    }

    @DeleteMapping("delete/{memberId}")
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public ResponseEntity<?> deleteOneMemberByMemberId(@PathVariable Long memberId)
    {

        memberService.deleteOneMemberById(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
