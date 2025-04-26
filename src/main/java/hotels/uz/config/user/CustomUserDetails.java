package hotels.uz.config.user;

import hotels.uz.entity.hotel_profile.ProfileHotelEntity;
import hotels.uz.entity.hotel_user.ProfileUserEntity;
import hotels.uz.enums.ProfileRole;
import jakarta.persistence.Column;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {

    @Getter
    private Integer userId;
    @Getter
    private Integer hotelId;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private ProfileRole role;
    private final Collection<? extends GrantedAuthority> authorities;

    private String propertyType;
    private String propertyAddress;
    private int numberOfRooms;
    private Boolean hasParking;
    private int starRating;
    private String propertyDescription;

    public CustomUserDetails(ProfileUserEntity profileUser, ProfileRole role) {
        this.userId = profileUser.getProfileUserId();
        this.firstName = profileUser.getFirstName();
        this.lastName = profileUser.getLastName();
        this.phoneNumber = profileUser.getPhoneNumber();
        this.email = profileUser.getEmail();
        this.password = profileUser.getPassword();
        this.role = role;
        this.authorities = List.of(role); // yoki map qilish kerak bo'lsa
    }

    public CustomUserDetails(ProfileHotelEntity profileHotel, ProfileRole role) {
        this.hotelId = profileHotel.getProfileHotelId();
        this.firstName = profileHotel.getFirstName();
        this.lastName = profileHotel.getLastName();
        this.phoneNumber = profileHotel.getPhoneNumber();
        this.email = profileHotel.getEmail();
        this.password = profileHotel.getPassword();
        this.role = role;
        this.authorities = List.of(role);
        this.propertyType = profileHotel.getPropertyType();
        this.propertyAddress = profileHotel.getPropertyAddress();
        this.numberOfRooms = profileHotel.getNumberOfRooms();
        this.hasParking = profileHotel.getHasParking();
        this.starRating = profileHotel.getStarRating();
        this.propertyDescription = profileHotel.getPropertyDescription();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
