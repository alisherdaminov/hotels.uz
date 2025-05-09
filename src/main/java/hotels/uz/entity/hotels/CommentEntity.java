package hotels.uz.entity.hotels;

import hotels.uz.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Getter
@Setter
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String commentId;
    @Column(name = "comment_text")
    private String commentText;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "profile_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private UserEntity userEntity;

    @Column(name = "hotel_details_id")
    private String hotelsDetailsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_details_id", insertable = false, updatable = false)
    private HotelsDetailsEntity hotelsDetailsComments;
}
