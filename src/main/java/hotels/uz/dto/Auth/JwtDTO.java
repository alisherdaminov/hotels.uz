package hotels.uz.dto.Auth;

import hotels.uz.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtDTO {
    private Integer userId;
    private String username;
    private List<ProfileRole> roleList;
}
