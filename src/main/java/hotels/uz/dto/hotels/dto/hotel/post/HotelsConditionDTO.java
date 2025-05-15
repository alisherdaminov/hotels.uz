package hotels.uz.dto.hotels.dto.hotel.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelsConditionDTO {
    private String conditionId;
    private String conditionName;
   // private HotelsDetailsDTO hotelsDetailsDTO;
}
