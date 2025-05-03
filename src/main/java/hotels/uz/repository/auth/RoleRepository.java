package hotels.uz.repository.auth;

import hotels.uz.entity.auth.RoleEntity;
import hotels.uz.enums.ProfileRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    @Transactional
    @Modifying
    void deleteByProfileUserId(Integer profileUserId);

    @Query("select p.profileRoles From RoleEntity p where p.profileUserId = ?1")
    List<ProfileRole> findByRolesProfileUserId(Integer profileUserId);

}
