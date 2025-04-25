package hotels.uz.dto.Auth.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import hotels.uz.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private ProfileRole role;
    private String email;
    private String jwtToken;
}
