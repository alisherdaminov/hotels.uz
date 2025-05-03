package hotels.uz.entity.hotels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "hotels_condition_table")
@Getter
@Setter
public class HotelsConditionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String hotelsConditionId;

    @Column(name = "condition_name_of_item")
    private String conditionNameOfItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_details_id", nullable = false)
    private HotelsDetailsEntity hotelsDetailsEntity;
}
