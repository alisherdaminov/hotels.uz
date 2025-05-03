package hotels.uz.entity.auth;

import hotels.uz.enums.ProfileRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "role_table")
@Getter
@Setter
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profileRoleId;

    @Column(name = "profile_user_id")
    private Integer profileUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_user_id", insertable = false, updatable = false)
    private UserEntity profileUser;

    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private ProfileRole profileRoles;

    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();
}
