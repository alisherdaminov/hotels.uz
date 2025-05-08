package hotels.uz.dto.hotels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MainAddsDTO {

    private String photoId;
    private String origenName;
    private String extension;
    private Long size;
    private String url;
    private LocalDateTime createdDate;
}
