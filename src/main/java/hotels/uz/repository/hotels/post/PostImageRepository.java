package hotels.uz.repository.hotels.post;

import hotels.uz.entity.hotels.post.PostImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImageEntity, String> {
}
