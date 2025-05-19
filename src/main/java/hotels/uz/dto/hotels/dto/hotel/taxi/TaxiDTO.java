package hotels.uz.dto.hotels.dto.hotel.taxi;

import com.fasterxml.jackson.annotation.JsonInclude;
import hotels.uz.dto.hotels.dto.hotel.post.HotelsDetailsDTO;
import hotels.uz.dto.hotels.dto.hotel.post.PostRegionImageDTO;
import hotels.uz.entity.hotels.taxi.TaxiCategoryEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxiDTO {
    private String taxiId;
    private String taxiTitle;
    private String taxiLogo;
    private String taxiPhoneNumber;
    private List<TaxiCategoryDTO> taxiCategoryList;
    private LocalDateTime createdDate;

}
