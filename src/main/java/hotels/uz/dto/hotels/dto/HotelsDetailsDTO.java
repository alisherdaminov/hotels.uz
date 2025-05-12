package hotels.uz.dto.hotels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import hotels.uz.dto.hotels.dto.likes.UserLikesDTO;
import hotels.uz.entity.hotels.likes.UserLikes;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
    private UserLikesDTO userLikes;
    private HotelsPostDTO hotelsPostDTO;
    private List<HotelsConditionDTO> conditionNameOfItemList = new ArrayList<>();
    private boolean ordered;
}

