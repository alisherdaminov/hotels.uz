package hotels.uz.service.hotels.post;

import hotels.uz.dto.hotels.created.hotel.post.BookingCreatedDTO;
import hotels.uz.dto.hotels.dto.hotel.post.BookingDTO;
import hotels.uz.entity.auth.UserEntity;
import hotels.uz.entity.hotels.post.BookingEntity;
import hotels.uz.entity.hotels.post.HotelsDetailsEntity;
import hotels.uz.repository.auth.UserRepository;
import hotels.uz.repository.hotels.post.BookingRepository;
import hotels.uz.repository.hotels.post.HotelDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private HotelDetailsRepository hotelDetailsRepository;
    @Autowired
    private UserRepository userRepository;

    public List<BookingDTO> createOrder(BookingCreatedDTO bookingCreatedDTO, Integer userId, String postId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        HotelsDetailsEntity post = hotelDetailsRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setUserId(user.getProfileUserId());
        bookingEntity.setPostId(post.getHotelsDetailsId());
        bookingEntity.setCheckInDate(bookingCreatedDTO.getCheckInDate());
        bookingEntity.setCheckOutDate(bookingCreatedDTO.getCheckOutDate());
        bookingRepository.save(bookingEntity);
        return toDTO(bookingEntity);
    }
    public List<BookingDTO> getBookingsByUserAndHotel(Integer userId, String postId) {
        List<BookingEntity> bookings = bookingRepository.findByUserIdAndPostId(userId, postId);
        return bookings.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public BookingDTO mapToDTO(BookingEntity bookingEntity) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(bookingEntity.getBookingId());
        bookingDTO.setUserId(bookingEntity.getUserId());
        bookingDTO.setPostId(bookingEntity.getPostId());
        bookingDTO.setCheckInDate(bookingEntity.getCheckInDate());
        bookingDTO.setCheckOutDate(bookingEntity.getCheckOutDate());
        return bookingDTO;
    }
    public List<BookingDTO> toDTO(BookingEntity bookingEntity) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(bookingEntity.getBookingId());
        bookingDTO.setUserId(bookingEntity.getUserId());
        bookingDTO.setPostId(bookingEntity.getPostId());
        bookingDTO.setCheckInDate(bookingEntity.getCheckInDate());
        bookingDTO.setCheckOutDate(bookingEntity.getCheckOutDate());
        return Collections.singletonList(bookingDTO);
    }

}
