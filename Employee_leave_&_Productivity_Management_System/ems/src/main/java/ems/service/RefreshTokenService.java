package ems.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ems.entity.RefreshToken;
import ems.entity.User;
import ems.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository repo;

    // 7 Days
    private static final long EXPIRY =
        7 * 24 * 60 * 60 * 1000;

    public RefreshToken createRefreshToken(User user) {

        // Remove old token
        repo.deleteByUser(user);

        RefreshToken token = new RefreshToken();

        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());

        token.setExpiryDate(
            Instant.now().plusMillis(EXPIRY)
        );

        return repo.save(token);
    }

    public RefreshToken verifyToken(String token) {

        RefreshToken refreshToken =
            repo.findByToken(token)
                .orElseThrow(() ->
                    new RuntimeException("Invalid Token"));

        if (refreshToken.getExpiryDate()
                .isBefore(Instant.now())) {

            repo.delete(refreshToken);

            throw new RuntimeException("Expired Token");
        }

        return refreshToken;
    }

    public void deleteByUser(User user) {

        repo.deleteByUser(user);
    }
}