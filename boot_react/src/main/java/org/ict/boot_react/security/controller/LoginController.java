package org.ict.boot_react.security.controller;

import org.ict.boot_react.member.model.dto.MemberDto;
import org.ict.boot_react.member.model.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    private final MemberService memberService;

    public LoginController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody MemberDto member){
        // 시큐리티 필터들을 거쳐서 토큰 검사가 완료되면 자동으로 연결
        // 로그인한 회원정보 조회해서, 로그인 요청한 클라이언트에게 응답 전동

        MemberDto loginMember = memberService.selectLogin(member);
        return ResponseEntity.ok(loginMember);
    }




}
