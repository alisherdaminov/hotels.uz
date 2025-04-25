package hotels.uz.enums;

import org.springframework.security.core.GrantedAuthority;

public enum ProfileRole implements GrantedAuthority {
    USER_ROLE,
    HOTEL_ROLE,
    ADMIN_ROLE;

    @Override
    public String getAuthority() {
        return name();
    }
}
