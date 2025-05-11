package hotels.uz.dto.hotels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelsPostDTO {
    private String postId;
    private String regionName;
    private String properties;
    private String description;
    private Integer averagePrice;
    private Integer dealsStarted;
    private PostImageDTO regionImage;
    private List<HotelsDetailsDTO> hotelsDetailsList;
    private LocalDateTime createdDate;
}
