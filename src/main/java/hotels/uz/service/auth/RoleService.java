package hotels.uz.service.auth;

import hotels.uz.entity.RoleEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RoleService {
    @Autowired
    private RoleRepository profileRoleRepository;

    public void createRole(Integer profileUserId, ProfileRole profileRole) {
        RoleEntity entity = new RoleEntity();
        entity.setProfileUserId(profileUserId);
        entity.setProfileRoles(profileRole);
        entity.setCreatedDate(LocalDateTime.now());
        profileRoleRepository.save(entity);
    }
    public void deleteRoleUser(Integer profileUserId) {
        profileRoleRepository.deleteByProfileUserId(profileUserId);
    }

}
