package hotels.uz.dto.Auth.hotel;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelRegistrationDTO {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Property type is required")
    private String propertyType;
    @NotBlank(message = "Property address is required")
    private String propertyAddress;
    @NotBlank(message = "Number of rooms is required")
    private int numberOfRooms;
    @NotBlank(message = "Has parking is required")
    private Boolean hasParking;
    @NotBlank(message = "Star rating is required")
    private int starRating;
    @NotBlank(message = "Property description is required")
    private String propertyDescription;
}

