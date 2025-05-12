package hotels.uz.dto.hotels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelsConditionDTO {
    private String conditionId;
    private String conditionName;
    private HotelsDetailsDTO hotelsDetailsDTO;
}
