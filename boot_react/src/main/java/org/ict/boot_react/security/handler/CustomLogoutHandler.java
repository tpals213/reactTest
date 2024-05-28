package org.ict.boot_react.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.ict.boot_react.member.model.dto.MemberDto;
import org.ict.boot_react.member.model.service.MemberService;
import org.ict.boot_react.security.jpa.entity.RefreshToken;
import org.ict.boot_react.security.jwt.util.JWTUtil;
import org.ict.boot_react.security.model.service.RefreshTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.Optional;

@Slf4j
public class CustomLogoutHandler implements LogoutHandler {

    // 의존성 주입 방법 2
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshService;
    private final MemberService memberService;

    public CustomLogoutHandler(JWTUtil jwtUtil, RefreshTokenService refreshService, MemberService memberService){
        this.jwtUtil = jwtUtil;
        this.refreshService = refreshService;
        this.memberService = memberService;
    }   // 의존성 주입을 위한 생성자

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 요청 헤더에서 'Authorization' 값을 추출
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")){
            // "Bearer" 다음부터 시작하는 실제 토큰 값을 추출
            String token = authorization.split(" ")[1];
            // 토큰에서 사용자 아이디를 추출
            String userid = jwtUtil.getUserIdFromToken(token);
            // 사용자 아이디(이메일)을 통해 사용자 정보 조회함
            MemberDto loginMember = memberService.selectMember(userid);
            if(loginMember != null){
                // 해당 사용자의 리프레시 토큰을 데이터 베이스에서 조회
                Optional<RefreshToken> refresh = refreshService.findByTokenValue(token);
                if(refresh.isPresent()){
                    RefreshToken refreshToken = refresh.get();
                    // 해당 사용자의 리프레시 토큰을 데이터 베이스에서 삭제
                    refreshService.deleteByRefresh(refreshToken.getTokenValue());

                }
            }
        }

        // 클라이언트에게 로그아웃 성공 보내기
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
