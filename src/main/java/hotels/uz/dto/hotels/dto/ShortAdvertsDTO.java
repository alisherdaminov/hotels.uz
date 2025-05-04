package hotels.uz.dto.hotels.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShortAdvertsDTO {
    private String shortAdvertsId;
    private String title;
    private String stayDescription;
    private String discountDescription;
}
