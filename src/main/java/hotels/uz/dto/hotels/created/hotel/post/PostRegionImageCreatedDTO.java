package hotels.uz.dto.hotels.created.hotel.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRegionImageCreatedDTO {
    @NotBlank(message = "Id is required")
    private String postRegionImageCreatedId;
}
