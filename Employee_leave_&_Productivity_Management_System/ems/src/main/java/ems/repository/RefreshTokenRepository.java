package ems.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ems.entity.RefreshToken;
import ems.entity.User;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser(User user);

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}   