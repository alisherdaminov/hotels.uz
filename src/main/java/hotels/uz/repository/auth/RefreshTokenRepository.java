package hotels.uz.repository.auth;

import hotels.uz.entity.auth.RefreshTokenEntity;
import hotels.uz.entity.auth.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Integer> {

    @EntityGraph(attributePaths = {"user"})
    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);

    @Transactional
    @Modifying
    void deleteByUser(UserEntity user);
}
