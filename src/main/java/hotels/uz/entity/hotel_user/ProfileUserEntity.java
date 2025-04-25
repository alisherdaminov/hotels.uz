package hotels.uz.entity.hotel_user;

import hotels.uz.entity.ProfileRoleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile_user")
@Getter
@Setter
public class ProfileUserEntity {

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
    @Column(name = "password")
    private String password;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @OneToOne(mappedBy = "profileUser", fetch = FetchType.LAZY)
    private ProfileRoleEntity profileRole;


}
