package hotels.uz.service.user;


import hotels.uz.dto.AppResponse;
import hotels.uz.dto.Auth.login.AuthDto;
import hotels.uz.dto.Auth.user.UserProfileDTO;
import hotels.uz.dto.Auth.user.UserRegistrationDTO;
import hotels.uz.entity.hotel_user.ProfileUserEntity;
import hotels.uz.enums.AppLanguage;
import hotels.uz.repository.ProfileRoleRepository;
import hotels.uz.repository.ProfileUserRepository;
import hotels.uz.service.internationalization.ResourceBundleService;
import hotels.uz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProfileUserService {

    @Autowired
    private ProfileUserRepository profileUserRepository;
    @Autowired
    private ProfileRoleRepository profileRoleRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AppResponse<String> userRegistrationService(UserRegistrationDTO userRegistrationDTO, AppLanguage language) {
        Optional<ProfileUserEntity> optional = profileUserRepository.findByEmail(userRegistrationDTO.getEmail());
        if (optional.isPresent()) {
            return new AppResponse<>(resourceBundleService.getMessage("user.exists", language));
        } else {
            ProfileUserEntity profileUserEntity = new ProfileUserEntity();
            profileUserEntity.setFirstName(userRegistrationDTO.getFirstName());
            profileUserEntity.setLastName(userRegistrationDTO.getLastName());
            profileUserEntity.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
            profileUserEntity.setEmail(userRegistrationDTO.getEmail());
            profileUserEntity.setPassword(bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword()));
            profileUserEntity.setCreatedDate(LocalDateTime.now());
            profileUserRepository.save(profileUserEntity);
            return new AppResponse<>(resourceBundleService.getMessage("user.successfully.registered", language));
        }
    }

    public UserProfileDTO userLogin(AuthDto authDto, AppLanguage language) {
        Optional<ProfileUserEntity> optional = profileUserRepository.findByEmail(authDto.getEmail());
        if (optional.isEmpty()) {
             new AppResponse<>(resourceBundleService.getMessage("user.not.found", language));
        }
        ProfileUserEntity profileUserEntity = optional.get();
        if (!bCryptPasswordEncoder.matches(authDto.getPassword(), profileUserEntity.getPassword())) {
             new AppResponse<>(resourceBundleService.getMessage("wrong.password", language));
        }
        return getUserProfileLogInResponse(profileUserEntity);
    }

    public UserProfileDTO getUserProfileLogInResponse(ProfileUserEntity profileUser) {
        UserProfileDTO response = new UserProfileDTO();
        response.setUserId(profileUser.getProfileUserId());
        response.setFirstName(profileUser.getFirstName());
        response.setLastName(profileUser.getLastName());
        response.setPhoneNumber(profileUser.getPhoneNumber());
        response.setEmail(profileUser.getEmail());
        response.setRole(profileRoleRepository.findRoleByProfileUserId(profileUser.getProfileUserId())); // role set by profileUserId
        response.setJwtToken(JwtUtil.encode(profileUser.getEmail(), profileUser.getProfileUserId(), response.getRole()));// token is created
        return response;
    }

}