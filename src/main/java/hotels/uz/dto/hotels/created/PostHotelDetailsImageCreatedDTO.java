package hotels.uz.dto.hotels.created;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostHotelDetailsImageCreatedDTO {
    @NotBlank(message = "Id is required")
    private String postHotelDetailsImageCreatedId;
}
