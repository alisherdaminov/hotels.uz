package hotels.uz.repository.hotels.taxi;

import hotels.uz.entity.hotels.taxi.TaxiCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiCategoryRepository extends JpaRepository<TaxiCategoryEntity, String> {
}
