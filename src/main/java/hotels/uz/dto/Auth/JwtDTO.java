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
    private String username;
    private Integer id;
    private List<ProfileRole> rolesList;
}
