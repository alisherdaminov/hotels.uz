package hotels.uz.config.validation;

import hotels.uz.entity.UserEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.repository.RoleRepository;
import hotels.uz.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AuthRepository profileUserRepository;
    @Autowired
    private RoleRepository profileRoleRepository;
//    @Autowired
//    private ResourceBundleService resourceBundleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.printf("USERNAME: -> " + username);
        Optional<UserEntity> optional = profileUserRepository.findByUsername(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserEntity user = optional.get();
        List<ProfileRole> rolesList = profileRoleRepository.findByRolesProfileUserId(user.getProfileUserId());
        // .orElseThrow(() -> new UsernameNotFoundException(resourceBundleService.getMessage("user.not.found", AppLanguage.EN)));
        return new CustomUserDetails(user, rolesList);


    }
}
//@Override
//public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    System.out.printf("loadUserByUsername: " + username);
//    Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(username);
//    if (optional.isEmpty()) {
//        throw new UsernameNotFoundException("User not found");
//    }
//    ProfileEntity profile = optional.get();
//    List<ProfileRoleEnum> roleEnumList = profileRoleRepository.findAllRolesByProfileId(profile.getId());
//    return new CustomUserDetails(profile, roleEnumList);
//}
//}
