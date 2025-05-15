package hotels.uz.dto.hotels.created.hotel.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingCreatedDTO {

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
