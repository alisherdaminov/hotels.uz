package hotels.uz.entity.hotel_profile;

import hotels.uz.entity.ProfileRoleEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile_hotel")
@Getter
@Setter
public class ProfileHotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profileHotelId;
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
    @Column(name = "property_type")
    private String propertyType;
    @Column(name = "property_address")
    private String propertyAddress;
    @Column(name = "number_of_rooms")
    private int numberOfRooms;
    @Column(name = "has_parking")
    private Boolean hasParking;
    @Column(name = "star_rating")
    private int starRating;
    @Column(name = "property_description")
    private String propertyDescription;
    @Column(name = "local_date")
    private LocalDateTime createdDate;

    @OneToOne(mappedBy = "profileHotel", fetch = FetchType.LAZY)
    private ProfileRoleEntity profileRole;


}
