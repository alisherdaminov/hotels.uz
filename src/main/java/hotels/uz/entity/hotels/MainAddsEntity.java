package hotels.uz.entity.hotels;

import hotels.uz.entity.auth.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "main_adds")
@Setter
@Getter
public class MainAddsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String mainAddsId;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "adds_expiration_date")
    private LocalDateTime addsExpirationDate;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "profile_id")
    private Integer userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private UserEntity userMainAdds;
}
