package hotels.uz.entity.hotels.likes;

import hotels.uz.entity.auth.UserEntity;
import hotels.uz.entity.hotels.post.HotelsDetailsEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_likes")
@Getter
@Setter
public class UserLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "liked")
    private boolean liked;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",insertable=false, updatable=false)
    private UserEntity userLikes;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private HotelsDetailsEntity hotelsDetailsPosts;
}
