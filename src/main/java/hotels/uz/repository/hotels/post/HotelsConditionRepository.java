package hotels.uz.repository.hotels.post;

import hotels.uz.entity.hotels.post.HotelsConditionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelsConditionRepository extends JpaRepository<HotelsConditionEntity, String> {
}
