package hotels.uz.dto.hotels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDTO {
    private String postId;
    private String regionName;
    private String properties;
    private String description;
    private Integer averagePrice;
    private Integer dealsStarted;
    private String regionImage;
    private List<HotelsDetailsDTO> hotelsDetailsDTOList;
    private LocalDateTime createdDate;
}
