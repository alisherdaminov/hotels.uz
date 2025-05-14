package hotels.uz.dto.hotels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import hotels.uz.dto.hotels.dto.likes.UserLikesDTO;
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
    private String discountAddsTitle;
    private String discountAddsDescription;
    private String roomsDeluxeName;
    private PostHotelDetailsImageDTO hotelPostImageId;
    private UserLikesDTO userLikes;
    private List<BookingDTO> bookingList;
    private List<HotelsConditionDTO> conditionNameOfItemList = new ArrayList<>();
    private boolean ordered;
}

