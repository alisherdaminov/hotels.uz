package hotels.uz.entity.hotels;

import hotels.uz.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels_table")
@Getter
@Setter
public class HotelsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String hotelsId;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "properties")
    private String properties;

    @Column(name = "description")
    private String description;

    @Column(name = "average_price")
    private int averagePrice;

    @Column(name = "deals_started")
    private int dealsStarted;

    @Column(name = "region_image")
    private String regionImage;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "profile_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",insertable=false, updatable=false)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "hotelsEntity", fetch = FetchType.LAZY)
    private List<HotelsDetailsEntity> hotelsDetailsEntityList = new ArrayList<>();
}
