package hotels.uz.service.auth;

import hotels.uz.dto.Auth.ApplicationData;
import hotels.uz.dto.Auth.CreatedUserDTO;
import hotels.uz.dto.Auth.LoginDTO;
import hotels.uz.dto.Auth.ResponseDTO;
import hotels.uz.entity.UserEntity;
import hotels.uz.enums.AppLanguage;
import hotels.uz.enums.ProfileRole;
import hotels.uz.repository.RoleRepository;
import hotels.uz.repository.AuthRepository;
import hotels.uz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationData<String> userRegistration(CreatedUserDTO dto, AppLanguage language) {
        Optional<UserEntity> optional = authRepository.findByUsername(dto.getUsername());
        if (optional.isPresent()) {
            return new ApplicationData<>("user.exists");
        }
        UserEntity entity = new UserEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setCreatedDate(LocalDateTime.now());
        authRepository.save(entity);
        roleService.createRole(entity.getProfileUserId(), ProfileRole.USER_ROLE);
        return new ApplicationData<>("User successfully registered");
    }

    public ApplicationData<String> hotelRegistration(CreatedUserDTO dto, AppLanguage language) {
        Optional<UserEntity> optional = authRepository.findByUsername(dto.getUsername());
        if (optional.isPresent()) {
            return new ApplicationData<>("user.exists");
        }
        UserEntity entity = new UserEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setPropertyType(dto.getPropertyType());
        entity.setPropertyAddress(dto.getPropertyAddress());
        entity.setNumberOfRooms(dto.getNumberOfRooms());
        entity.setStarRating(dto.getStarRating());
        entity.setPropertyDescription(dto.getPropertyDescription());
        entity.setHasParking(dto.getHasParking());
        entity.setCreatedDate(LocalDateTime.now());
        authRepository.save(entity);
        roleService.createRole(entity.getProfileUserId(), ProfileRole.HOTEL_ROLE);
        return new ApplicationData<>("Hotel successfully registered");
    }


    public ApplicationData<ResponseDTO> login(LoginDTO loginRequest, AppLanguage language) {
        Optional<UserEntity> optionalUser = authRepository.findByUsername(loginRequest.getUsername());
        System.out.println("optionalUser = " + optionalUser);
        if (optionalUser.isEmpty()) {
            return new ApplicationData<>("user.not.found");
        }

        UserEntity user = optionalUser.get();
        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new ApplicationData<>("wrong.password");
        }

        ResponseDTO userDTO = buildUserDTO(user);
        return new ApplicationData<>(userDTO, "success", new Date());
    }

    private ResponseDTO buildUserDTO(UserEntity user) {
        ResponseDTO dto = new ResponseDTO();
        dto.setUserId(user.getProfileUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setEmail(user.getEmail());
        dto.setPropertyType(user.getPropertyType());
        dto.setPropertyAddress(user.getPropertyAddress());
        dto.setNumberOfRooms(user.getNumberOfRooms());
        dto.setHasParking(user.getHasParking());
        dto.setStarRating(user.getStarRating());
        dto.setPropertyDescription(user.getPropertyDescription());
        dto.setCreatedDate(user.getCreatedDate());

        List<ProfileRole> roles = roleRepository.findByRolesProfileUserId(user.getProfileUserId());
        dto.setRoles(roles);

        if (!roles.isEmpty()) {
            dto.setJwtToken(JwtUtil.encode(user.getEmail(), user.getProfileUserId(), roles));
        }

        return dto;
    }
}
