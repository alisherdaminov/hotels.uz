package hotels.uz.entity.auth.profile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_profile_image")
@Getter
@Setter
public class UserProfileImageEntity {

    @Id
    private String userProfileImageId;
    @Column(name = "path")
    private String path;
    @Column(name = "original_name")
    private String originalName;
    @Column(name = "extension")
    private String extension;
    @Column(name = "size")
    private Long size;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
