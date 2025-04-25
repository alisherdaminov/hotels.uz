package hotels.uz.repository;

import hotels.uz.entity.hotel_profile.ProfileHotelEntity;
import hotels.uz.entity.hotel_user.ProfileUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileHotelRepository extends JpaRepository<ProfileHotelEntity, Integer> {
    Optional<ProfileHotelEntity> findByEmail(String email);
}
