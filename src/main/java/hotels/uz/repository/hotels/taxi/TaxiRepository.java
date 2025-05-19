package hotels.uz.repository.hotels.taxi;

import hotels.uz.entity.hotels.taxi.TaxiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TaxiRepository extends JpaRepository<TaxiEntity, String> {

}
