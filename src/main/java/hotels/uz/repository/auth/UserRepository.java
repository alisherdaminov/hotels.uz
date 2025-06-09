package hotels.uz.repository.auth;

import hotels.uz.entity.auth.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {


    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    Optional<UserEntity> findByUsername(@Param("username") String username);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.createdDate = CURRENT_TIMESTAMP WHERE u.profileUserId = ?1")
    int updateLogoutTime(Integer profileUserId);

    @Transactional
    @Modifying
    @Query("update UserEntity u set u.firstName = ?1, u.lastName = ?2, " +
            "u.phoneNumber = ?3, u.email = ?4 where u.profileUserId = ?5")
    void updateUserDetails(String firstName,
                                 String lastName, String phoneNumber, String email,Integer userId);

}

