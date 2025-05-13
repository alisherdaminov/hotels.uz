package hotels.uz.dto.hotels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {

    private String bookingId;
    private Integer userId;
    private String postId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
