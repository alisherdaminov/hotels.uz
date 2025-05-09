package hotels.uz.dto.hotels.created;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentsCreatedDTO {

    @NotBlank(message = "Title is required")
    private String commentsText;
}