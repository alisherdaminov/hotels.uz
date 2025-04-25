package hotels.uz.entity;

import hotels.uz.entity.hotel_profile.ProfileHotelEntity;
import hotels.uz.entity.hotel_user.ProfileUserEntity;
import hotels.uz.enums.ProfileRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile_role")
@Getter
@Setter
public class ProfileRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profileRoleId;

    @Column(name = "profile_user_id")
    private Integer profileUserId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_user_id", insertable = false, updatable = false)
    private ProfileUserEntity profileUser;

    @Column(name = "profile_hotel_id")
    private Integer profileHotelId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_hotel_id", insertable = false, updatable = false)
    private ProfileHotelEntity profileHotel;

    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private ProfileRole profileRoles;

    @Column(name = "created_date")
    private LocalDateTime createdData;
}
