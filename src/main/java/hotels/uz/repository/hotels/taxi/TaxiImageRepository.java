package hotels.uz.repository.hotels.taxi;

import hotels.uz.entity.hotels.taxi.TaxiImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaxiImageRepository extends JpaRepository<TaxiImageEntity, String> {

}
