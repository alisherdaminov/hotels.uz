package hotels.uz.controller.auth;

import hotels.uz.dto.AppResponse;
import hotels.uz.dto.Auth.login.AuthDto;
import hotels.uz.dto.Auth.user.UserProfileDTO;
import hotels.uz.dto.Auth.user.UserRegistrationDTO;
import hotels.uz.enums.AppLanguage;
import hotels.uz.service.user.ProfileUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth User Controller", description = "Api's authorization and authentication for Users")
public class AuthUserController {
    @Autowired
    private ProfileUserService profileUserService;

    @PostMapping("/user/registration")
    public ResponseEntity<AppResponse<String>> userRegistration(@Valid
                                                                @RequestBody UserRegistrationDTO userRegistrationDTO,
                                                                @RequestHeader(value = "Accept-Language", defaultValue = "EN") AppLanguage language) {
        return ResponseEntity.ok().body(profileUserService.userRegistrationService(userRegistrationDTO, language));
    }

    @PostMapping("/user/login")
    public ResponseEntity<UserProfileDTO> userLogin(@Valid
                                                    @RequestBody AuthDto authDto,
                                                    @RequestHeader(value = "Accept-Language", defaultValue = "EN") AppLanguage language) {
        return ResponseEntity.ok().body(profileUserService.userLogin(authDto, language));
    }
}
