package hotels.uz.dto.hotels.created;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MainAddsCreatedDTO {

    @NotBlank(message = "Title name is required")
    @Length(min = 5, max = 100, message = "Title name must be min-5 to max-100 characters")
    private String title;
    @NotBlank(message = "Description is required")
    @Length(min = 5, max = 50, message = "Description must be min-5 to max-100 characters")
    private String description;
    private String image;
}
