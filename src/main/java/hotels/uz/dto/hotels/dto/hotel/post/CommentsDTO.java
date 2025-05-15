package hotels.uz.dto.hotels.dto.hotel.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentsDTO {
    private String commentsId;
    private String commentsText;
    private LocalDateTime createdDate;
}