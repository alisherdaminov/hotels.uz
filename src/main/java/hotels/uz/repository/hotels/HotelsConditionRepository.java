package hotels.uz.repository.hotels;

import hotels.uz.entity.hotels.HotelsConditionEntity;
import hotels.uz.entity.hotels.HotelsDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelsConditionRepository extends JpaRepository<HotelsConditionEntity, String> {
}
