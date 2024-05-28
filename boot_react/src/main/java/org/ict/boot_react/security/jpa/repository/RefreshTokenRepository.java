package org.ict.boot_react.security.jpa.repository;

import org.ict.boot_react.security.jpa.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByTokenValue(String tokenValue);
    Boolean existsByTokenValue(String tokenValue);
    void deleteByTokenValue(String tokenValue);
    Optional<RefreshToken> findByUserId(UUID id);
}
