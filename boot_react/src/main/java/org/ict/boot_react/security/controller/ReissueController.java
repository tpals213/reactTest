package org.ict.boot_react.security.controller;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ict.boot_react.member.model.dto.MemberDto;
import org.ict.boot_react.member.model.service.MemberService;
import org.ict.boot_react.security.jpa.entity.RefreshToken;
import org.ict.boot_react.security.jwt.util.JWTUtil;
import org.ict.boot_react.security.model.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController // 이 클래스가 REST 컨트롤러임을 나타내며, Spring MVC에서 HTTP 요청을 처리하는 핸들러가 됩니다.
@Slf4j // Lombok의 로깅을 위한 어노테이션, 이 클래스 내부에서는 로그를 찍는 부분이 생략되어 있지만, 로깅 목적으로 사용됩니다.
public class ReissueController {

    private final Long accessExpiredMs; // 액세스 토큰의 유효 시간 (밀리초 단위)

    private final JWTUtil jwtUtil; // JWT 처리를 위한 유틸리티 클래스

    private final MemberService userService; // 사용자 정보를 처리하는 서비스
    private final RefreshTokenService refreshService; // 리프레시 토큰 관련 서비스

    // 의존성 주입을 위한 생성자
    public ReissueController(JWTUtil jwtUtil, MemberService userService, RefreshTokenService refreshService) {
        this.userService = userService;
        this.refreshService = refreshService;
        this.accessExpiredMs = 600000L; // 예: 10분
        this.jwtUtil = jwtUtil;
    }

    // access 토큰 재발급 요청용 컨트롤러 (단, 리프레시 토큰이 정상 상태 일 때만 재발급 처리 가능)
    @PostMapping("/reissue") // POST 요청을 '/reissue' 경로로 매핑합니다.
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        // HTTP 요청에서 'Authorization' 헤더를 통해 리프레시 토큰을 받아옵니다.
        String refresh = request.getHeader("Authorization");
        if (refresh == null || !refresh.startsWith("Bearer ")) { // 토큰이 없거나 Bearer 타입이 아니면 에러 반환
            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        String token = refresh.substring("Bearer ".length()); // 실제 토큰 값을 추출합니다.

        // 토큰 만료 여부 검사
        try {
            if (jwtUtil.isTokenExpired(token)) {
                return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
            }
        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우 예외 처리
            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 리프레시 토큰이 맞는지 카테고리로 확인합니다.
        String category = jwtUtil.getCategoryFromToken(token);
        if (!category.equals("refresh")) {
            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }

        // 토큰에서 사용자 이메일을 추출합니다.
        String userid = jwtUtil.getUserIdFromToken(token);

        // 사용자 정보 조회
        MemberDto member = userService.selectMember(userid);
        if (member == null) {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }

        // 리프레시 토큰이 유효한지 확인합니다.
        Optional<RefreshToken> refreshTokenOptional = refreshService.findByTokenValue(token);
        if (refreshTokenOptional.isEmpty() || !refreshTokenOptional.get().getMember().equals(member)) {
            return new ResponseEntity<>("refresh token not found or does not match", HttpStatus.BAD_REQUEST);
        }

        // 리프레시 토큰의 상태 검증
        RefreshToken refreshToken = refreshTokenOptional.get();
        if (!refreshToken.getStatus().equals("activated")) {
            return new ResponseEntity<>("refresh token invalid or expired", HttpStatus.BAD_REQUEST);
        }

        // 새로운 액세스 토큰 생성
        String access = jwtUtil.generateToken(userid, "access", accessExpiredMs/1000);

        // 응답에 새로운 액세스 토큰 추가
        response.addHeader("Authorization", "Bearer " + access);

        // 클라이언트가 Authorization 헤더를 읽을 수 있도록 설정
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        // 성공적으로 새 토큰을 발급받았을 때의 응답
        return new ResponseEntity<>(HttpStatus.OK);
    }
}