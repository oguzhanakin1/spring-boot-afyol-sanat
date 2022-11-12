package com.example.photographerApp.controller;

import com.example.photographerApp.model.Member;
import com.example.photographerApp.request.MemberRequest;
import com.example.photographerApp.response.MemberResponse;
import com.example.photographerApp.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IMemberService memberService;

    @Autowired
    public MemberController(IMemberService memberService)
    {
        this.memberService = memberService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public String member()
    {
        return "Üye sayfasına hoşgeldiniz.";
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
        Member member = memberService.findOneMemberById(memberId).orElseThrow(()->
                new RuntimeException("member not found with id: " + memberId));

        return ResponseEntity.ok(new MemberResponse(member));
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public ResponseEntity<?> createMember(@RequestBody MemberRequest request)
    {
        return new ResponseEntity<>(memberService.createMember(request), HttpStatus.CREATED);
    }

    @PutMapping("update/{memberId}")
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public ResponseEntity<?> updateMember(@PathVariable Long memberId,
                                         @RequestBody MemberRequest request)
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
