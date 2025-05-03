package hotels.uz.controller;

import hotels.uz.dto.Auth.*;
import hotels.uz.enums.AppLanguage;
import hotels.uz.service.auth.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth User Controller", description = "Api's authorization and authentication for Users")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/user/registration")
    public ResponseEntity<ApplicationData<String>> userRegistration(@Valid
                                                                    @RequestBody CreatedUserDTO createdUserDTO,
                                                                    @RequestHeader(value = "Accept-Language", defaultValue = "EN") AppLanguage language) {
        return ResponseEntity.ok().body(authService.userRegistration(createdUserDTO, language));
    }


    @PostMapping("/hotel/registration")
    public ResponseEntity<ApplicationData<String>> hotelRegistration(@Valid
                                                                     @RequestBody CreatedUserDTO createdUserDTO,
                                                                     @RequestHeader(value = "Accept-Language", defaultValue = "EN") AppLanguage language) {
        return ResponseEntity.ok().body(authService.hotelRegistration(createdUserDTO, language));
    }

    @PostMapping("/login")
    public ResponseEntity<ApplicationData<ResponseDTO>> login(@Valid
                                                              @RequestBody LoginDTO loginDTO,
                                                              @RequestHeader(value = "Accept-Language", defaultValue = "EN") AppLanguage language) {
        return ResponseEntity.ok().body(authService.login(loginDTO, language));
    }

    //    @PostMapping("/refresh")
//    public ApplicationData<AccessTokenResponse> refresh(@RequestBody RefreshTokenRequest refreshToken) {
//        return authService.refreshToken(refreshToken);
//    }
    @PostMapping("/logout/{userId}")
    public ResponseEntity<ApplicationData<String>> logoutUser(@PathVariable("userId") Integer userId, HttpServletRequest request) {
        boolean success = authService.logoutByUserId(userId);
        if (success) {
            request.getSession().invalidate(); // optional
            return ResponseEntity.ok().body(new ApplicationData<>("successfully logout!!!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApplicationData<>("User not found"));
        }
    }

}
