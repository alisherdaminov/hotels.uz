package hotels.uz.config.validation;

import hotels.uz.entity.UserEntity;
import hotels.uz.enums.ProfileRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String userName;
    private String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private String propertyType;
    private String propertyAddress;
    private String numberOfRooms;
    private Boolean hasParking;
    private String starRating;
    private String propertyDescription;


    public CustomUserDetails(UserEntity profileUser, List<ProfileRole> rolesList) {
        this.userId = profileUser.getProfileUserId();
        this.firstName = profileUser.getFirstName();
        this.lastName = profileUser.getLastName();
        this.phoneNumber = profileUser.getPhoneNumber();
        this.email = profileUser.getEmail();
        this.userName = profileUser.getUsername();
        this.password = profileUser.getPassword();
        this.authorities = rolesList.stream().map(role -> new SimpleGrantedAuthority(role.name())).toList();
        this.propertyType = profileUser.getPropertyType();
        this.propertyAddress = profileUser.getPropertyAddress();
        this.numberOfRooms = profileUser.getNumberOfRooms();
        this.hasParking = profileUser.getHasParking();
        this.starRating = profileUser.getStarRating();
        this.propertyDescription = profileUser.getPropertyDescription();
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
        return userName;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
