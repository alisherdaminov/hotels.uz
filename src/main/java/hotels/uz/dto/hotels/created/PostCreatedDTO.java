package hotels.uz.dto.hotels.created;

import hotels.uz.dto.hotels.dto.HotelsDetailsDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostCreatedDTO {
    @NotBlank(message = "Region name is required")
    @Length(min = 5, max = 70, message = "Region name must be min-5 to max-100 characters")
    private String regionName;
    @NotBlank(message = "Properties is required")
    @Length(min = 5, max = 50, message = "Properties must be min-5 to max-100 characters")
    private String properties;
    @NotBlank(message = "Description is required")
    @Length(min = 5, max = 100, message = "Description must be min-5 to max-100 characters")
    private String description;
    @NotNull(message = "Average price is required")
    private int averagePrice;
    @NotNull(message = "Deals started price is required")
    private int dealsStarted;
    @NotBlank(message = "Region image is required")
    private String regionImage;
    private List<HotelsDetailsDTO> hotelsDetailsDTOList;
}
