package hotels.uz.dto.Auth.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileImageDTO {

    private String userProfileImageId;
    private String origenName;
    private String extension;
    private Long size;
    private String url;
    private LocalDateTime createdDate;
}
