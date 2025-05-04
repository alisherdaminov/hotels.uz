package hotels.uz.entity.hotels;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "short_adverts")
@Getter
@Setter
public class ShortAdvertsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String shortAdvertsId;
    @Column(name = "title")
    private String title;
    @Column(name = "stay_description")
    private String stayDescription;
    @Column(name = "discount_description")
    private String discountDescription;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
