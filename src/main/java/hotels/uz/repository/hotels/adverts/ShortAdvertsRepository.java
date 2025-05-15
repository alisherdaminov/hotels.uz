package hotels.uz.repository.hotels.adverts;

import hotels.uz.entity.hotels.adverts.ShortAdvertsEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ShortAdvertsRepository extends JpaRepository<ShortAdvertsEntity, String> {

    @Query("SELECT s FROM ShortAdvertsEntity s WHERE s.shortAdvertsId = :shortAdvertsId")
    ShortAdvertsEntity findByShortAdvertsId(@Param("shortAdvertsId") String shortAdvertsId);

    @Query("SELECT COUNT(s) > 0 FROM ShortAdvertsEntity s WHERE s.advertsExpirationDate > :now")
    boolean isExistExpirationDateInAdverts(@Param("now") LocalDateTime now);

    @Query("SELECT s FROM ShortAdvertsEntity s WHERE s.advertsExpirationDate > :now ORDER BY s.advertsExpirationDate ASC")
    Optional<ShortAdvertsEntity> findOneNotExpiredAdverts(@Param("now") LocalDateTime now);

    @Transactional
    @Modifying
    @Query("UPDATE ShortAdvertsEntity s SET s.title = ?1, s.stayDescription = ?2, " +
            "s.discountDescription = ?3 WHERE s.shortAdvertsId = ?4")
    void updateShortAdverts(String title, String stayDescription, String discountDescription, String shortAdvertsId);

}
