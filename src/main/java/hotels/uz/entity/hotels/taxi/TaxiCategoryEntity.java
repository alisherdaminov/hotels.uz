package hotels.uz.entity.hotels.taxi;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "taxi_category_table")
@Getter
@Setter
public class TaxiCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String taxiId;
    @Column(name = "taxi_category_name")
    private String taxiCategoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taxi_category")
    private TaxiEntity taxiEntity;

}
