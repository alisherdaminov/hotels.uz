package hotels.uz.entity.hotels.taxi;

import hotels.uz.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private String taxiPhoneNumber;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "profile_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private UserEntity userTaxi;

    @OneToMany(mappedBy = "taxiEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaxiCategoryEntity> taxiCategoryList;
}
