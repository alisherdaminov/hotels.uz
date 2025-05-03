package hotels.uz.service.auth;

import hotels.uz.entity.auth.RefreshTokenEntity;
import hotels.uz.entity.auth.UserEntity;
import hotels.uz.repository.auth.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository tokenRepository;

    public RefreshTokenEntity createRefreshToken(UserEntity user) {
        tokenRepository.deleteByUser(user);
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUser(user);
        // 7 days
        long refreshTokenDurationMs = 7 * 24 * 60 * 60 * 1000L;
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        return tokenRepository.save(refreshToken);
    }

    public Optional<RefreshTokenEntity> findByRefreshToken(String token) {
        return tokenRepository.findByRefreshToken(token);
    }

    public boolean verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            tokenRepository.delete(token);
            return false;
        }
        return true;
    }

    public void deleteByUser(UserEntity user) {
        tokenRepository.deleteByUser(user);
    }
}
