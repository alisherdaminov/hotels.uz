package hotels.uz.entity.auth;

import hotels.uz.entity.hotels.CommentEntity;
import hotels.uz.entity.hotels.HotelsEntity;
import hotels.uz.entity.hotels.MainAddsEntity;
import hotels.uz.entity.hotels.ShortAdvertsEntity;
import hotels.uz.entity.hotels.likes.UserLikes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_table")
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profileUserId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "property_address")
    private String propertyAddress;

    @Column(name = "number_of_rooms")
    private String numberOfRooms;

    @Column(name = "has_parking")
    private Boolean hasParking;

    @Column(name = "star_rating")
    private String starRating;

    @Column(name = "property_description")
    private String propertyDescription;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @OneToMany(mappedBy = "userLikes", fetch = FetchType.LAZY)
    private List<UserLikes> userLikes;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<HotelsEntity> hotels;

    @Column(name = "photo_id")
    private String photoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
    private UserEntity userMainAdds;

    @OneToOne(mappedBy = "usersAdverts", fetch = FetchType.LAZY)
    private ShortAdvertsEntity shortAdvertsEntity;

    @OneToMany(mappedBy = "profileUser", fetch = FetchType.LAZY)
    private List<RoleEntity> roleEntityList;
    //comment
    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityUserList;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private RefreshTokenEntity refreshToken;
}
