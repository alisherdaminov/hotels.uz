package hotels.uz.dto.Auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import hotels.uz.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String username;
    private List<ProfileRole> roles;
    private String propertyType;
    private String propertyAddress;
    private String numberOfRooms;
    private Boolean hasParking;
    private String starRating;
    private String propertyDescription;
    private String jwtToken;
    private LocalDateTime createdDate;

}
