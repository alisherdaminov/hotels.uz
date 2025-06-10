package hotels.uz.service.auth;

import hotels.uz.entity.auth.RefreshTokenEntity;
import hotels.uz.entity.auth.UserEntity;
import hotels.uz.repository.auth.RefreshTokenRepository;
import hotels.uz.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${jwt.refreshExpirationMs}")
    private Long refreshTokenDurationMs;
    @Autowired
    private RefreshTokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

    public RefreshTokenEntity createRefreshToken(Integer userId) {
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        return tokenRepository.save(refreshToken);
    }

    public Optional<RefreshTokenEntity> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            tokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }
        return token;
    }


    public void deleteByUser(UserEntity user) {
        tokenRepository.deleteByUser(user);
    }
}
