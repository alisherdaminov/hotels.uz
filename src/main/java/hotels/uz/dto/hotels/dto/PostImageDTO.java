package hotels.uz.dto.hotels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostImageDTO {

    private String postImageId;
    private String origenName;
    private String extension;
    private Long size;
    private String url;
    private LocalDateTime createdDate;
}
