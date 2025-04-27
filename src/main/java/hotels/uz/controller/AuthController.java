package hotels.uz.controller;

import hotels.uz.dto.Auth.ApplicationData;
import hotels.uz.dto.Auth.CreatedUserDTO;
import hotels.uz.dto.Auth.LoginDTO;
import hotels.uz.dto.Auth.ResponseDTO;
import hotels.uz.enums.AppLanguage;
import hotels.uz.service.auth.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth User Controller", description = "Api's authorization and authentication for Users")
public class AuthController {
    @Autowired
    private AuthService profileUserService;

    @PostMapping("/user/registration")
    public ResponseEntity<ApplicationData<String>> userRegistration(@Valid
                                                                    @RequestBody CreatedUserDTO createdUserDTO,
                                                                    @RequestHeader(value = "Accept-Language", defaultValue = "EN") AppLanguage language) {
        return ResponseEntity.ok().body(profileUserService.userRegistration(createdUserDTO, language));
    }


    @PostMapping("/hotel/registration")
    public ResponseEntity<ApplicationData<String>> hotelRegistration(@Valid
                                                                     @RequestBody CreatedUserDTO createdUserDTO,
                                                                     @RequestHeader(value = "Accept-Language", defaultValue = "EN") AppLanguage language) {
        return ResponseEntity.ok().body(profileUserService.hotelRegistration(createdUserDTO, language));
    }

    @PostMapping("/login")
    public ResponseEntity<ApplicationData<ResponseDTO>> login(@Valid
                                                                  @RequestBody LoginDTO loginDTO,
                                                                  @RequestHeader(value = "Accept-Language", defaultValue = "EN") AppLanguage language) {
        return ResponseEntity.ok().body(profileUserService.login(loginDTO, language));
    }
}
