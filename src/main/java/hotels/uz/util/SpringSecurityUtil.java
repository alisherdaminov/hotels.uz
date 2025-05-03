package hotels.uz.util;

import hotels.uz.config.validation.CustomUserDetails;
import hotels.uz.entity.auth.UserEntity;
import hotels.uz.enums.ProfileRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUtil {
    public static CustomUserDetails getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (CustomUserDetails) authentication.getPrincipal();
    }

    public static Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getUserId();
    }

    // for admin or user roles checking true or false response
    public static Boolean hasRole(ProfileRole requiredRole) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().anyMatch(authority ->
                authority.getAuthority().equals(requiredRole.name()));
    }
}
