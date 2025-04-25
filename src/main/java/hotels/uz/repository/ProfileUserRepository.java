package hotels.uz.repository;

import hotels.uz.entity.hotel_user.ProfileUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileUserRepository extends JpaRepository<ProfileUserEntity, Integer> {

    Optional<ProfileUserEntity> findByEmail(String email);
}
