package hotels.uz.repository.hotels;

import hotels.uz.entity.hotels.MainAddsEntity;
import hotels.uz.entity.hotels.PostImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PostImageRepository extends JpaRepository<PostImageEntity, String> {
}
