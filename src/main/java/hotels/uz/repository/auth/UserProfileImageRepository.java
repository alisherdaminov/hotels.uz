package hotels.uz.repository.auth;

import hotels.uz.entity.auth.profile.UserProfileImageEntity;
import hotels.uz.entity.hotels.taxi.TaxiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileImageRepository extends JpaRepository<UserProfileImageEntity, String> {

}
