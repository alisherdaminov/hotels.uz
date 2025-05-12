package hotels.uz.dto.hotels.dto.likes;

import lombok.Data;

@Data
public class UserLikesDTO {
    private Integer userLikesId;
    private boolean isLiked;
    private Integer userId;
    private String postId;
}