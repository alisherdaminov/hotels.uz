package hotels.uz.dto.hotels.created;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShortAdvertsCreatedDTO {

    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Stay description is required")
    private String stayDescription;
    @NotBlank(message = "Discount description is required")
    private String discountDescription;
    @NotNull(message = "Card number is required")
    private Long cardNumber;
    @NotNull(message = "Card cvv is required")
    private String cardCvv;
    @NotNull(message = "amount_of_money")
    private Integer amountOfMoney;

}
