package hotels.uz.entity.hotels.taxi;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "taxi_table")
@Getter
@Setter
public class TaxiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String taxiId;
    @Column(name = "taxi_title")
    private String taxiTitle;
    @Column(name = "taxi_logo")
    private String taxiLogo;
    @Column(name = "taxi_PhoneNumber")
    private Long taxiPhoneNumber;

    @OneToMany(mappedBy = "taxiEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaxiCategoryEntity> taxiCategoryList;
}
