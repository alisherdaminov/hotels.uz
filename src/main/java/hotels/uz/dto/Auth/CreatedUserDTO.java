package hotels.uz.dto.Auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatedUserDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    // Hotel-specific fields
    private String propertyType;
    private String propertyAddress;
    private String numberOfRooms;
    private Boolean hasParking;
    private String starRating;
    private String propertyDescription;
}

