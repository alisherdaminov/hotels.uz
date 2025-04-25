package hotels.uz.config.user;

import hotels.uz.entity.ProfileRoleEntity;
import hotels.uz.entity.hotel_profile.ProfileHotelEntity;
import hotels.uz.entity.hotel_user.ProfileUserEntity;
import hotels.uz.enums.AppLanguage;
import hotels.uz.enums.ProfileRole;
import hotels.uz.repository.ProfileHotelRepository;
import hotels.uz.repository.ProfileRoleRepository;
import hotels.uz.repository.ProfileUserRepository;
import hotels.uz.service.internationalization.ResourceBundleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileUserRepository profileUserRepository;
    @Autowired
    private ProfileHotelRepository profileHotelRepository;
    @Autowired
    private ProfileRoleRepository profileRoleRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //finding user
        Optional<ProfileUserEntity> userEntityOptional = profileUserRepository.findByEmail(email);
        if (userEntityOptional.isPresent()) {
            ProfileUserEntity user = userEntityOptional.get();
            ProfileRoleEntity role = profileRoleRepository.findByProfileUserId(user.getProfileUserId())
                    .orElseThrow(() -> new UsernameNotFoundException(resourceBundleService.getMessage("user.not.found", AppLanguage.EN)));
            return new CustomUserDetails(user.getEmail(), user.getPassword(), role.getProfileRoles(), user.getProfileUserId());
        }

        //finding hotel owner
        Optional<ProfileHotelEntity> hotelEntityOptional = profileHotelRepository.findByEmail(email);
        if (hotelEntityOptional.isPresent()) {
            ProfileHotelEntity hotel = hotelEntityOptional.get();
            ProfileRoleEntity role = profileRoleRepository.findByProfileHotelId(hotel.getProfileHotelId())
                    .orElseThrow(() -> new UsernameNotFoundException(resourceBundleService.getMessage("hotel.not.found", AppLanguage.EN)));
            return new CustomUserDetails(hotel.getEmail(), hotel.getPassword(), role.getProfileRoles(), hotel.getProfileHotelId());
        }

        throw new UsernameNotFoundException(resourceBundleService.getMessage("user.not.found", AppLanguage.EN));
    }
}
