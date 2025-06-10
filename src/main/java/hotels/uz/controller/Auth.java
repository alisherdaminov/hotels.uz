package hotels.uz.controller;

import hotels.uz.dto.Auth.*;
import hotels.uz.entity.auth.RefreshTokenEntity;
import hotels.uz.entity.auth.RoleEntity;
import hotels.uz.enums.AppLanguage;
import hotels.uz.service.auth.AuthService;
import hotels.uz.service.auth.RefreshTokenService;
import hotels.uz.util.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Api's authorization and authentication for Users and Hotels owners")
public class Auth {
    @Autowired
    private AuthService authService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/admin/registration")
    public ResponseEntity<ApiResponse<String>> adminRegistration(@Valid
                                                                 @RequestBody UserCreatedDTO createdUserDTO,
                                                                 @RequestHeader(value = "Accept-Language", defaultValue = "EN") AppLanguage language) {
        return ResponseEntity.ok().body(authService.adminRegistration(createdUserDTO, language));
    }


    @PostMapping("/user/registration")
    public ResponseEntity<ApiResponse<String>> userRegistration(@Valid
                                                                @RequestBody UserCreatedDTO createdUserDTO,
                                                                @RequestHeader(value = "Accept-Language", defaultValue = "EN") AppLanguage language) {
        return ResponseEntity.ok().body(authService.userRegistration(createdUserDTO, language));
    }

    @PostMapping("/hotel/registration")
    public ResponseEntity<ApiResponse<String>> hotelRegistration(@Valid
                                                                 @RequestBody UserCreatedDTO createdUserDTO,
                                                                 @RequestHeader(value = "Accept-Language", defaultValue = "EN") AppLanguage language) {
        return ResponseEntity.ok().body(authService.hotelRegistration(createdUserDTO, language));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<ResponseDTO>> login(@Valid
                                                          @RequestBody LoginDTO loginDTO,
                                                          @RequestHeader(value = "Accept-Language", defaultValue = "EN") AppLanguage language) {
        return ResponseEntity.ok().body(authService.login(loginDTO, language));
    }

    @PostMapping("/refresh")
    public ApiResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        RefreshTokenEntity refreshToken = refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        String token = JwtUtil.encode(refreshToken.getUser().getUsername(), refreshToken.getUser().getProfileUserId(),
                refreshToken.getUser().getRoleEntityList().stream().map(RoleEntity::getProfileRoles).toList());
        return new ApiResponse(token, "Success", new Date());
    }


    @PostMapping("/logout/{userId}")
    public ResponseEntity<ApiResponse<String>> logoutUser(@PathVariable("userId") Integer userId, HttpServletRequest request) {
        boolean success = authService.logoutByUserId(userId);
        if (success) {
            request.getSession().invalidate(); // optional
            return ResponseEntity.ok().body(new ApiResponse<>("successfully logout!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("User not found"));
        }
    }

}