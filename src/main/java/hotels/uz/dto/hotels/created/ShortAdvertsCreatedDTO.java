package hotels.uz.dto.hotels.created;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortAdvertsCreatedDTO {

    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Stay description is required")
    private String stayDescription;
    @NotBlank(message = "Discount description is required")
    private String discountDescription;
}
