package org.ict.boot_react.security.model.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ict.boot_react.security.jpa.entity.RefreshToken;
import org.ict.boot_react.security.jpa.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Transactional
@Service
public class RefreshTokenService {
    // 의존성 주입 방법 1 :
    // @Autowired
    // private RefreshTokenRepository refreshTokenRepository;

    // 의존성 주입 방법 2 :
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository){
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken save(RefreshToken refreshToken){
        return refreshTokenRepository.save(refreshToken);
    }

    public Optional<RefreshToken> findByTokenValue(String tokenValue){
        return refreshTokenRepository.findByTokenValue(tokenValue);
    }

    public boolean existsByTokenValue(String tokenValue){
        return refreshTokenRepository.existsByTokenValue(tokenValue);
    }

    public void deleteByRefresh(String tokenValue){
        refreshTokenRepository.deleteByTokenValue(tokenValue);
    }

    public Optional<RefreshToken> findByUserId(UUID id){
        return refreshTokenRepository.findByUserId(id);
    }

}
