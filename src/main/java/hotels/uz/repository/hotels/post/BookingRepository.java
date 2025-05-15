package hotels.uz.repository.hotels.post;

import hotels.uz.entity.hotels.post.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, String> {

    @Query("SELECT b FROM BookingEntity b WHERE b.userId = :userId AND b.postId = :postId")
    List<BookingEntity> findByUserIdAndPostId(Integer userId, String postId);
}
