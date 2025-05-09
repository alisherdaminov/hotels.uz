package hotels.uz.dto.Auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Data
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private T data;
    private String message;
    private Date timestamp;

    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(T data, String message, Date timestamp) {
        this.data = data;
        this.message = message;
        this.timestamp = timestamp;
    }
}
