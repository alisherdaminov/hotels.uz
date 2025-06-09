package hotels.uz.dto.Auth.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileImageCreatedDTO {
    @NotBlank(message = "Id is required")
    private Integer userProfileImageCreatedId;
}
