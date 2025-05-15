package hotels.uz.repository.hotels.post;

import hotels.uz.entity.hotels.post.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<CommentEntity, String> {

}