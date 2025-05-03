package hotels.uz.dto.hotels.created;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class HotelsCreatedConditionDTO {

    @NotBlank(message = "Condition name of item is required")
    @Length(min = 5, max = 50, message = "Condition name of item must be min-5 to max-50 characters")
    private String conditionNameOfItem;
}
