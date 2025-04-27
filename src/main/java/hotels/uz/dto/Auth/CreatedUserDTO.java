package hotels.uz.dto.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatedUserDTO {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "user_name")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    // Hotel-specific fields
    private String propertyType;
    private String propertyAddress;
    private String numberOfRooms;
    private Boolean hasParking;
    private String starRating;
    private String propertyDescription;
}

