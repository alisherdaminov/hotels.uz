package hotels.uz.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.security.SecureRandom;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "refresh_token_table")
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer refreshTokenId;

    @Column(nullable = false, unique = true,columnDefinition = "TEXT")
    private String refreshToken;
    @Column(nullable = false)
    private Instant expiryDate;
    @OneToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private UserEntity user;

    @PrePersist
    public void generateRefreshToken() {
        if (this.refreshToken == null) {
            this.refreshToken = generateRandomString(); // 64 characters
        }
    }

    private String generateRandomString() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(500);
        for (int i = 0; i < 500; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}


