package org.ict.boot_react.security.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.ict.boot_react.member.model.dto.MemberDto;
import org.ict.boot_react.member.model.service.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component  // 스프링 컨테이너에 의해 관리되는 컴포넌트로 선언
public class JWTUtil {
    // JWT 생성과 검증에 사용될 비밀키와 만료 시간을 필드로 선언
    private final SecretKey secretKey;
    private final MemberService memberService;

    // 생성자를 통해 application.properties에서 정의한 jwt 비밀키와 만료시간, MemberService를 주입
    public JWTUtil(@Value("${jwt.secret}") String secret, MemberService memberService) {
        // 비밀키 초기화함, 이 비밀키는 서명에 사용됨
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        this.memberService = memberService;
        // 로그인한 사용자 정보를 조회하기 위해 인스턴스 초기화
    }

    // JWT 생성 : 로그인한 사용자의 아이디를 저장함
    // userId : 로그인 요청사용자 아이디 받음, category : 토큰의 종류(access, refresh)
    // expiredMs : 만료시간
    public String generateToken(String userId, String category, Long expiredMs) {
        // MemberService 사용해서 DB에서 로그인한 사용자 정보 조회
        MemberDto memberDto = memberService.selectMember(userId);

        // 사용자 정보가 없는 경우, UsernameNotFoundException 발생시킴 - 스프링 제공
        if(memberDto == null) {
            throw new UsernameNotFoundException("User : " + userId + "not found");
        }

        // 사용자의 관리자 여부 확인
        String adminYn = memberDto.getAdminYn();

        // JWT 생성 : 사용자아이디를 주제(subject)에 넣고 관리자 여부는 클레임으로 추가
        return Jwts.builder()
                .setSubject(userId)
                .claim("admin", adminYn)    // "admin"이라는 클레임에 관리자여부 등록
                .claim("category", category)  // 토큰의 종류 등록 (access, refresh)
                // .claim("username", memberDto.getUserName()) // 추가하고 싶은 값 등록
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))    // 만료 시간
                .signWith(secretKey, SignatureAlgorithm.HS256)
                // 비밀키와 HS256 알고리즘으로 JWT를 서명함
                .compact(); // JWT 문자열을 만듦
    }

    // jwt에서 사용자 아이디 추출하는 메소드
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    // JWT의 만료 여부 확인용 메소드
    public boolean isTokenExpired(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());   // True 만료
    }

    // JWT의 관리자 여부 추출 메소드
    public String  getAdminFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.get("admin", String.class);
    }

    // JWT의 등록된 토큰 종류 추출 메소드
    public String  getCategoryFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        return claims.get("category", String.class);
    }

}
