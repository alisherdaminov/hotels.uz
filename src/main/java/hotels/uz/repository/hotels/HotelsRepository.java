package hotels.uz.repository.hotels;

import hotels.uz.entity.hotels.HotelsDetailsEntity;
import hotels.uz.entity.hotels.HotelsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelsRepository extends JpaRepository<HotelsEntity, String> {
    @Query("SELECT h FROM HotelsEntity h LEFT JOIN FETCH h.hotelsDetailsEntityList")
    List<HotelsEntity> findAllWithDetails();

}
