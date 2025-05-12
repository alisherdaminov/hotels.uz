package hotels.uz.dto.hotels.created.likes;


import lombok.Data;

@Data
public class UserLikesCreateDTO {
    private boolean liked;
    private Integer userId;
    private Integer postId;
}