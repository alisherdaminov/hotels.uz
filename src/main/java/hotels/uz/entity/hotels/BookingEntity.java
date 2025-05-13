package hotels.uz.entity.hotels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "booking_table")
@Getter
@Setter
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String bookingId;

    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "post_id")
    private String postId;
    @Column(name = "check_in_date")
    private LocalDate checkInDate;
    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotels_details_booking_id")
    private HotelsDetailsEntity hotelsDetailsEntity;
}
