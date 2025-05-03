package hotels.uz.repository.hotels;

import hotels.uz.entity.hotels.HotelsDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelDetailsRepository extends JpaRepository<HotelsDetailsEntity, String> {
}
