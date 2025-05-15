package hotels.uz.dto.hotels.created.hotel.taxi;

import hotels.uz.dto.hotels.created.hotel.post.HotelsCreatedConditionDTO;
import hotels.uz.dto.hotels.created.hotel.post.PostHotelDetailsImageCreatedDTO;
import hotels.uz.dto.hotels.created.hotel.post.PostRegionImageCreatedDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TaxiCreatedDTO {
    @NotBlank(message = "Taxi name is required")
    @Length(min = 5, max = 15, message = "Taxi name must be min-5 to max-15 characters")
    private String taxiTitle;
    private String taxiLogo;
    private Long taxiPhoneNumber;
    private List<TaxiCategoryCreatedDTO> categoryCreatedList = new ArrayList<>();
}
