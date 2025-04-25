package hotels.uz.repository;

import hotels.uz.entity.ProfileRoleEntity;
import hotels.uz.enums.ProfileRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRoleRepository extends JpaRepository<ProfileRoleEntity, Integer> {

    Optional<ProfileRoleEntity> findByProfileUserId(Integer profileUserId);

    Optional<ProfileRoleEntity> findByProfileHotelId(Integer profileHotelId);

    @Transactional
    @Modifying
    void deleteByProfileUserId(Integer profileUserId);

    @Transactional
    @Modifying
    void deleteByProfileHotelId(Integer profileHotelId);

    @Query("select p.roles From ProfileRoleEntity p where p.profileUserId = ?1")
    ProfileRole findRoleByProfileUserId(Integer profileUserId);
}
