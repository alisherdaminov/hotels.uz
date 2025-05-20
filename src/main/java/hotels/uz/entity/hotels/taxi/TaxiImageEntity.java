package hotels.uz.entity.hotels.taxi;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "taxi_image_table")
@Getter
@Setter
public class TaxiImageEntity {

    @Id
    private String taxiImageId;
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
