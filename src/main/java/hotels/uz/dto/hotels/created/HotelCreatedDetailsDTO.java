package hotels.uz.dto.hotels.created;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class HotelCreatedDetailsDTO {
    @NotBlank(message = "Hotel name is required")
    @Length(min = 5, max = 70, message = "Hotel name must be min-5 to max-70 characters")
    private String hotelName;
    @NotBlank(message = "Location name is required")
    @Length(min = 5, max = 50, message = "Location name must be min-5 to max-50 characters")
    private String locationShortDescription;
    @NotBlank(message = "Short description name is required")
    @Length(min = 5, max = 50, message = "Short description must be min-5 to max-50 characters")
    private String hotelShortDescription;
    @NotBlank(message = "Room short description is required")
    @Length(min = 5, max = 50, message = "Room short description must be min-5 to max-50 characters")
    private String roomShortDescription;
    @NotBlank(message = "Price short description is required")
    @Length(min = 5, max = 50, message = "Price short description must be min-5 to max-50 characters")
    private String priceShortDescription;
    private int totalPrice;
    private int discountPrice;
    @NotBlank(message = "Hotel short title name is required")
    @Length(min = 5, max = 70, message = "Hotel short title must be min-5 to max-70 characters")
    private String hotelsShortTitle;
    @NotBlank(message = "Cancellation title name is required")
    @Length(min = 5, max = 70, message = "Cancellation Title must be min-5 to max-70 characters")
    private String cancellationTitle;
    @NotBlank(message = "Payment description is required")
    @Length(min = 5, max = 70, message = "Payment description must be min-5 to max-70 characters")
    private String paymentDescription;
    @NotBlank(message = "Breakfast included description is required")
    @Length(min = 5, max = 70, message = "Breakfast included description must be min-5 to max-70 characters")
    private String breakfastIncludedDescription;
    @NotNull(message = "Photo is required")
    private PostImageCreatedDTO hotelDetailsImage;
    private boolean isOrdered;
    @NotBlank(message = "Discount adds title is required")
    @Length(min = 5, max = 70, message = "Discount adds title must be min-5 to max-70 characters")
    private String discountAddsTitle;
    @NotBlank(message = "Discount adds description is required")
    @Length(min = 5, max = 70, message = "Discount adds description must be min-5 to max-70 characters")
    private String discountAddsDescription;
    @NotBlank(message = "Rooms deluxe name is required")
    @Length(min = 5, max = 70, message = "Rooms deluxe name must be min-5 to max-70 characters")
    private String roomsDeluxeName;
    private List<HotelsCreatedConditionDTO> conditionNameOfItemDTOList;
}
