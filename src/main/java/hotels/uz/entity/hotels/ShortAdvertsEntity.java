package hotels.uz.entity.hotels;

import hotels.uz.entity.auth.UserEntity;
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
    @Column(name = "card_number")
    private Long cardNumber;
    @Column(name = "card_cvv")
    private String cardCvv;
    @Column(name = "amount_of_money")
    private Integer amountOfMoney;
    @Column(name = "adverts_expiration_date")
    private LocalDateTime advertsExpirationDate;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "profile_id")
    private Integer userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private UserEntity usersAdverts;

}
