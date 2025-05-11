package hotels.uz.dto.hotels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelsDetailsDTO {
    private String hotelsDetailsId;
    private String hotelName;
    private String locationShortDescription;
    private String hotelShortDescription;
    private String roomShortDescription;
    private String priceShortDescription;
    private Integer totalPrice;
    private Integer discountPrice;
    private String hotelsShortTitle;
    private String cancellationTitle;
    private String paymentDescription;
    private String breakfastIncludedDescription;
    private PostImageDTO hotelImage;
    private String discountAddsTitle;
    private String discountAddsDescription;
    private String roomsDeluxeName;
    private List<HotelsConditionDTO> conditionNameOfItemList;
    private boolean ordered;
}

