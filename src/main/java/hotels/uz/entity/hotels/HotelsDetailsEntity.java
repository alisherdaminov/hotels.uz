package hotels.uz.entity.hotels;

import hotels.uz.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels_details_table")
@Getter
@Setter
public class HotelsDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String hotelsDetailsId;
    @Column(name = "hotels_name")
    private String hotelName;
    @Column(name = "location_short_description")
    private String locationShortDescription;
    @Column(name = "hotel_short_description")
    private String hotelShortDescription;
    @Column(name = "room_short_description")
    private String roomShortDescription;
    @Column(name = "price_short_description")
    private String priceShortDescription;
    @Column(name = "total_price")
    private int totalPrice;
    @Column(name = "discount_price")
    private int discountPrice;
    @Column(name = "hotels_short_title")
    private String hotelsShortTitle;
    @Column(name = "cancellation_title")
    private String cancellationTitle;
    @Column(name = "cancellation_description")
    private String paymentDescription;
    @Column(name = "breakfast_included_title")
    private String breakfastIncludedDescription;
    @Column(name = "hotel_image")
    private String hotelImage;
    @Column(name = "is_ordered")
    private boolean isOrdered;
    @Column(name = "discount_adds_title")
    private String discountAddsTitle;
    @Column(name = "discount_adds_description")
    private String discountAddsDescription;
    @Column(name = "rooms_deluxe_name")
    private String roomsDeluxeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelsEntity hotelsEntity;

    @OneToMany(mappedBy = "hotelsDetailsEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HotelsConditionEntity> hotelsConditionEntityList = new ArrayList<>();
}
