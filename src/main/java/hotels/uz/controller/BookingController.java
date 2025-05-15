package hotels.uz.controller;

import hotels.uz.dto.Auth.ApiResponse;
import hotels.uz.dto.hotels.created.hotel.post.BookingCreatedDTO;
import hotels.uz.dto.hotels.dto.hotel.post.BookingDTO;
import hotels.uz.service.hotels.post.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/create/{userId}/{hotelsDetailsId}")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> createBooking(@RequestBody BookingCreatedDTO dto,
                                                                       @PathVariable("userId") Integer userId,
                                                                       @PathVariable("hotelsDetailsId") String hotelsDetailsId) {
        return ResponseEntity.ok().body(new ApiResponse<>(bookingService.createOrder(dto, userId, hotelsDetailsId),
                "Success", new Date()));
    }

    @GetMapping("/fetch/{userId}/{hotelsDetailsId}")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> fetchBooking(
            @PathVariable("userId") Integer userId,
            @PathVariable("hotelsDetailsId") String hotelsDetailsId) {
        return ResponseEntity.ok().body(new ApiResponse<>(bookingService.getBookingsByUserAndHotel(userId, hotelsDetailsId),
                "Success", new Date()));
    }

}