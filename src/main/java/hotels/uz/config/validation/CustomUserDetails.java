package hotels.uz.config.validation;

import hotels.uz.entity.auth.UserEntity;
import hotels.uz.enums.ProfileRole;
import lombok.Getter;
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
    private String username;
    private String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private String propertyType;
    private String propertyAddress;
    private String numberOfRooms;
    private Boolean hasParking;
    private String starRating;
    private String propertyDescription;


    public CustomUserDetails(UserEntity user, List<ProfileRole> rolesList) {
        System.out.println("user ID:CustomUserDetails --------> " + user.getProfileUserId());
        this.userId = user.getProfileUserId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = rolesList.stream().map(role -> new SimpleGrantedAuthority(role.name())).toList();
        this.propertyType = user.getPropertyType();
        this.propertyAddress = user.getPropertyAddress();
        this.numberOfRooms = user.getNumberOfRooms();
        this.hasParking = user.getHasParking();
        this.starRating = user.getStarRating();
        this.propertyDescription = user.getPropertyDescription();
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
        return username;
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

