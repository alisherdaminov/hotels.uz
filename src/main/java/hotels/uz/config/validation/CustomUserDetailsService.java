package hotels.uz.config.validation;

import hotels.uz.entity.auth.UserEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.repository.auth.RoleRepository;
import hotels.uz.repository.auth.UserRepository;
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
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.printf("loadUserByUsername: " + username);
        Optional<UserEntity> optional = userRepository.findByUsername(username);
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserEntity profile = optional.get();
        List<ProfileRole> roleEnumList = roleRepository.findByRolesProfileUserId(profile.getProfileUserId());
        return new CustomUserDetails(profile, roleEnumList);
    }
}


