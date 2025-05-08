package hotels.uz.dto.hotels.created;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainAddsCreatedDTO {
    @NotBlank(message = "Id is required")
    private String id;
}
