package hotels.uz.repository.hotels;

import hotels.uz.entity.hotels.MainAddsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MainAddsRepository extends JpaRepository<MainAddsEntity, String> {


    @Query("SELECT COUNT(s) > 0 FROM MainAddsEntity s WHERE s.addsExpirationDate > :now")
    boolean isExistExpirationDate(@Param("now") LocalDateTime now);

    @Query("SELECT m MainAddsEntity m WHERE m.mainAddsId = :mainAddsId")
    MainAddsEntity findByMainAddsId(@Param("mainAddsId") String mainAddsId);


}
