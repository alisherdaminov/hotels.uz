package hotels.uz.repository.hotels;

import hotels.uz.entity.hotels.ShortAdvertsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortAdvertsRepository extends JpaRepository<ShortAdvertsEntity, String> {

    @Transactional
    @Modifying
    @Query("UPDATE ShortAdvertsEntity s SET s.title = ?1, s.stayDescription = ?2, s.discountDescription = ?3 WHERE s.shortAdvertsId = ?4")
    void updateShortAdverts( ShortAdvertsEntity entity, String shortAdvertsId);
}
