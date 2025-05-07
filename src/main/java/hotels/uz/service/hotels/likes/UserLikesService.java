package hotels.uz.service.hotels.likes;

import hotels.uz.entity.auth.UserEntity;
import hotels.uz.entity.hotels.HotelsDetailsEntity;
import hotels.uz.entity.hotels.likes.UserLikes;
import hotels.uz.repository.auth.UserRepository;
import hotels.uz.repository.hotels.HotelDetailsRepository;
import hotels.uz.repository.hotels.likes.UserLikesRepository;
import hotels.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLikesService {

    @Autowired
    private UserLikesRepository userLikesRepository;
    @Autowired
    private HotelDetailsRepository hotelDetailsRepository;
    @Autowired
    private UserRepository userRepository;

    public boolean isLiked(String postId, Integer userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        HotelsDetailsEntity post = hotelDetailsRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Optional<UserLikes> like = userLikesRepository.findByUserAndHotel(user, post);

        if (like.isPresent()) {
            UserLikes userLikes = like.get();
            userLikes.setLiked(!userLikes.isLiked()); // Toggle like status
            userLikes.setUserId(SpringSecurityUtil.getCurrentUserId());
            userLikesRepository.save(userLikes);
            return userLikes.isLiked(); // Return current status
        } else {
            UserLikes newLike = new UserLikes();
            newLike.setUserLikes(user);
            newLike.setHotelsDetailsPosts(post);
            newLike.setLiked(true);
            newLike.setUserId(SpringSecurityUtil.getCurrentUserId());
            userLikesRepository.save(newLike);
            return true;
        }
    }

    public long getLikeCount(String postId) {
        HotelsDetailsEntity hotelDetailsEntity = hotelDetailsRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        return userLikesRepository.countLikes(hotelDetailsEntity);
    }


}
