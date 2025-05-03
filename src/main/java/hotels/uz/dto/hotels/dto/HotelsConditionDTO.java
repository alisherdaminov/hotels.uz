package hotels.uz.dto.hotels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)// null degan qiymatlarni olmaydi
public class HotelsConditionDTO {
    private String conditionName;
   // private LocalDateTime createdDate;
}
