package hotels.uz.service.hotels.likes;

import hotels.uz.dto.hotels.dto.likes.UserLikesDTO;
import hotels.uz.entity.auth.UserEntity;
import hotels.uz.entity.hotels.HotelsDetailsEntity;
import hotels.uz.entity.hotels.likes.UserLikes;
import hotels.uz.repository.auth.UserRepository;
import hotels.uz.repository.hotels.HotelDetailsRepository;
import hotels.uz.repository.hotels.likes.UserLikesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserLikesService {

    @Autowired
    private UserLikesRepository userLikesRepository;
    @Autowired
    private HotelDetailsRepository hotelDetailsRepository;
    @Autowired
    private UserRepository userRepository;

    public UserLikesDTO isLiked(String postId, Integer userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        HotelsDetailsEntity post = hotelDetailsRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Optional<UserLikes> optionalLike = userLikesRepository.findByUserAndHotel(user, post);

        UserLikes like;
        if (optionalLike.isPresent()) {
            like = optionalLike.get();
            like.setLiked(!like.isLiked());
        } else {
            like = new UserLikes();
            like.setUserLikes(user);
            like.setHotelsDetailsPosts(post);
            like.setLiked(true);
            like.setUserId(userId);
        }
        userLikesRepository.save(like);
        return toDTO(like);
    }

    public List<UserLikesDTO> findAllLikes() {
        List<UserLikes> userLikes = userLikesRepository.findAllLikes();
        return userLikes.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public UserLikesDTO toDTO(UserLikes entity) {
        UserLikesDTO dto = new UserLikesDTO();
        dto.setUserLikesId(entity.getId());
        dto.setLiked(entity.isLiked());
        dto.setUserId(entity.getUserId());
        dto.setPostId(entity.getHotelsDetailsPosts().getHotelsDetailsId());
        return dto;
    }


}
