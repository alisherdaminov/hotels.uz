package hotels.uz.repository.hotels.likes;

import hotels.uz.dto.hotels.dto.likes.UserLikesDTO;
import hotels.uz.entity.auth.UserEntity;
import hotels.uz.entity.hotels.HotelsDetailsEntity;
import hotels.uz.entity.hotels.likes.UserLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLikesRepository extends JpaRepository<UserLikes, Integer> {

    // this method checks if the user has already liked the post
    @Query("SELECT ul FROM UserLikes ul WHERE ul.userLikes = :user AND ul.hotelsDetailsPosts = :hotel")
    Optional<UserLikes> findByUserAndHotel(@Param("user") UserEntity user, @Param("hotel") HotelsDetailsEntity hotel);

    @Query("SELECT ul FROM UserLikes ul")
    List<UserLikes> findAllLikes();
}
