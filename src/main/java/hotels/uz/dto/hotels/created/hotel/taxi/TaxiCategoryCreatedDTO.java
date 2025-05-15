package hotels.uz.dto.hotels.created.hotel.taxi;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxiCategoryCreatedDTO {
    private String taxiCategoryName;
}
