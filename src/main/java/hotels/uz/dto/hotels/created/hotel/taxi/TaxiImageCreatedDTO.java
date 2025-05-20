package hotels.uz.dto.hotels.created.hotel.taxi;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxiImageCreatedDTO {
    @NotBlank(message = "Id is required")
    private String taxiImageCreatedId;
}
