package hotels.uz.service.auth;

import hotels.uz.dto.Auth.*;
import hotels.uz.entity.auth.UserEntity;
import hotels.uz.enums.AppLanguage;
import hotels.uz.enums.ProfileRole;
import hotels.uz.repository.auth.RoleRepository;
import hotels.uz.repository.auth.UserRepository;
import hotels.uz.repository.auth.RefreshTokenRepository;
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

    public ApiResponse<String> userRegistration(CreatedUserDTO dto, AppLanguage language) {
        Optional<UserEntity> optional = authRepository.findByUsername(dto.getUsername());
        if (optional.isPresent()) {
            return new ApiResponse<>("user.exists");
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
        return new ApiResponse<>("User successfully registered");
    }

    public ApiResponse<String> hotelRegistration(CreatedUserDTO dto, AppLanguage language) {
        Optional<UserEntity> optional = authRepository.findByUsername(dto.getUsername());
        if (optional.isPresent()) {
            return new ApiResponse<>("user.exists");
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
            dto.setJwtToken(JwtUtil.encode( user.getUsername(),user.getProfileUserId(), roles));
            //dto.setRefreshToken(refreshTokenService.createRefreshToken(user).getRefreshToken());
        }
        return dto;
    }
}
