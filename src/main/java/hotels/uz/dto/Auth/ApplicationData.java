package hotels.uz.dto.Auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationData<T> {
    private T data;
    private String message;
    private Date timestamp;

    public ApplicationData(String message) {
        this.message = message;
    }

    public ApplicationData(T data, String message, Date timestamp) {
        this.data = data;
        this.message = message;
        this.timestamp = timestamp;
    }
}
