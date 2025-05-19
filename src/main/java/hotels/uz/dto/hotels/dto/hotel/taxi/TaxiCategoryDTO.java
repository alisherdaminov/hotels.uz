package hotels.uz.dto.hotels.dto.hotel.taxi;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxiCategoryDTO {
    private String taxiCategoryId;
    private String taxiCategoryName;
}
