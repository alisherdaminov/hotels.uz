package hotels.uz.dto.hotels.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShortAdvertsDTO {
    private String shortAdvertsId;
    private String title;
    private String stayDescription;
    private String discountDescription;
    private Long cardNumber;
    private String cardCvv;
    private Integer amountOfMoney;
    private LocalDateTime createdDate;
    private LocalDateTime advertsExpirationDate;
}
