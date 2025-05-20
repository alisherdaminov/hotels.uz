package hotels.uz.dto.hotels.dto.hotel.taxi;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxiImageDTO {

    private String taxiImageId;
    private String origenName;
    private String extension;
    private Long size;
    private String url;
    private LocalDateTime createdDate;
}
