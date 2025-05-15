package hotels.uz.entity.hotels.adverts;

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
    private String photoId;
    @Column(name = "path")
    private String path;
    @Column(name = "extension")
    private String extension;
    @Column(name = "original_name")
    private String originalName;
    @Column(name = "size")
    private Long size;
    @Column(name = "image")
    private String image;
    @Column(name = "adds_expiration_date")
    private LocalDateTime addsExpirationDate;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

}
