package hotels.uz.service.auth;

import hotels.uz.dto.Auth.*;
import hotels.uz.dto.Auth.UserCreatedDTO;
import hotels.uz.entity.auth.UserEntity;
import hotels.uz.enums.AppLanguage;
import hotels.uz.enums.ProfileRole;
import hotels.uz.exceptions.AppBadException;
import hotels.uz.repository.auth.RoleRepository;
import hotels.uz.repository.auth.UserRepository;
import hotels.uz.repository.auth.RefreshTokenRepository;
import hotels.uz.service.auth.profile.UserProfileImageService;
import hotels.uz.util.JwtUtil;
import hotels.uz.util.SpringSecurityUtil;
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
    private UserRepository authRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RefreshTokenRepository tokenRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserProfileImageService userProfileImageService;

    public ApiResponse<String> adminRegistration(UserCreatedDTO dto, AppLanguage language) {
        Optional<UserEntity> optional = authRepository.findByUsername(dto.getUsername());
        if (optional.isPresent()) {
            return new ApiResponse<>("Admin exists");
        }
        UserEntity entity = new UserEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setUsername(dto.getUsername());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setCreatedDate(LocalDateTime.now());
        authRepository.save(entity);
        roleService.createRole(entity.getProfileUserId(), ProfileRole.ROLE_ADMIN);
        return new ApiResponse<>("Admin successfully registered");
    }

    public ApiResponse<String> userRegistration(UserCreatedDTO dto, AppLanguage language) {
        Optional<UserEntity> optional = authRepository.findByUsername(dto.getUsername());
        if (optional.isPresent()) {
            return new ApiResponse<>("User exists");
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
        roleService.createRole(entity.getProfileUserId(), ProfileRole.ROLE_USER);
        return new ApiResponse<>("User successfully registered");
    }

    public ApiResponse<String> hotelRegistration(UserCreatedDTO dto, AppLanguage language) {
        Optional<UserEntity> optional = authRepository.findByUsername(dto.getUsername());
        if (optional.isPresent()) {
            return new ApiResponse<>("Hotel exists");
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
        roleService.createRole(entity.getProfileUserId(), ProfileRole.ROLE_HOTEL);
        return new ApiResponse<>("Hotel successfully registered");
    }

    public ApiResponse<ResponseDTO> login(LoginDTO loginRequest, AppLanguage language) {
        Optional<UserEntity> optionalUser = authRepository.findByUsername(loginRequest.getUsername());
        System.out.println("optionalUser login fun ----> " + optionalUser);
        if (optionalUser.isEmpty()) {
            return new ApiResponse<>("user.not.found");
        }
        UserEntity user = optionalUser.get();
        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new ApiResponse<>("wrong.password");
        }
        ResponseDTO userDTO = buildUserDTO(user);
        return new ApiResponse<>(userDTO, "success", new Date());
    }


    public boolean logoutByUserId(Integer userId) {
        int updatedRows = authRepository.updateLogoutTime(userId);
        return updatedRows > 0;
    }

    public ResponseDTO updateUserDetails(Integer userId, UserCreatedDTO createdDTO) {
        UserEntity user = getUserById(userId);
        if (!SpringSecurityUtil.hasRole(ProfileRole.ROLE_USER) &&
                !user.getProfileUserId().equals(userId)) {
            throw new AppBadException("You do not have any permission to update!");
        }
        Integer oldImage = null;
        if (!createdDTO.getUserProfileImageCreatedDTO().getUserProfileImageCreatedId()
                .equals(user.getProfileUserId())) {
            oldImage = user.getProfileUserId();
        }
        user.setFirstName(createdDTO.getFirstName());
        user.setLastName(createdDTO.getLastName());
        user.setPhoneNumber(createdDTO.getPhoneNumber());
        user.setEmail(createdDTO.getEmail());
        user.setProfileUserId(createdDTO.getUserProfileImageCreatedDTO().getUserProfileImageCreatedId());

        authRepository.save(user);
        if (oldImage != null) {
            userProfileImageService.deleteUserImage(String.valueOf(oldImage));
        }
        return buildUserDTO(user);
    }

    private UserEntity getUserById(Integer userId) {
        Optional<UserEntity> optional = authRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new AppBadException("User is not found!");
        }
        return optional.get();
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
        dto.setUserProfileImage(userProfileImageService.userImage(user.getProfileImageId()));
        dto.setRoles(roles);
        if (!roles.isEmpty()) {
            dto.setJwtToken(JwtUtil.encode(user.getUsername(), user.getProfileUserId(), roles));
            //dto.setRefreshToken(refreshTokenService.createRefreshToken(user).getRefreshToken());
        }
        return dto;
    }
}
