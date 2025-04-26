package hotels.uz.service.role;

import hotels.uz.entity.ProfileRoleEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfileRoleService {
    // This is a ProfileRoleService which is used to create a role for a profile

    @Autowired
    private ProfileRoleRepository profileRoleRepository;

//    public void createRole(Integer profileUserId, Integer profileHotelId, ProfileRole profileRole) {
//        ProfileRoleEntity entity = new ProfileRoleEntity();
//        entity.setProfileUserId(profileUserId);
//        entity.setProfileHotelId(profileHotelId);
//        entity.setProfileRoles(profileRole);
//        entity.setCreatedData(LocalDateTime.now());
//        profileRoleRepository.save(entity);
//
//    }
    public void createRole(Integer profileUserId, Integer profileHotelId, ProfileRole profileRole) {
        ProfileRoleEntity entity = new ProfileRoleEntity();

        if (profileUserId != null) {
            entity.setProfileUserId(profileUserId);
        }

        if (profileHotelId != null) {
            entity.setProfileHotelId(profileHotelId);
        }

        entity.setProfileRoles(profileRole);
        entity.setCreatedDate(LocalDateTime.now());

        profileRoleRepository.save(entity);
    }

    public void deleteRoleUser(Integer profileUserId) {
        profileRoleRepository.deleteByProfileUserId(profileUserId);
    }

    public void deleteRoleHotel(Integer profileHotelId) {
        profileRoleRepository.deleteByProfileUserId(profileHotelId);
    }
}
