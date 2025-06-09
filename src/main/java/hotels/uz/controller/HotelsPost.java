package hotels.uz.controller;

import hotels.uz.dto.Auth.ApiResponse;
import hotels.uz.dto.hotels.created.hotel.post.PostCreatedDTO;
import hotels.uz.dto.hotels.created.hotel.post.QueryCreatedDTO;
import hotels.uz.dto.hotels.dto.hotel.post.HotelsPostDTO;
import hotels.uz.service.hotels.post.HotelsPostsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel")
@Tag(name = "Hotels Post", description = "Hotel owner can create post that can be seen by all users")
public class HotelsPost {

    @Autowired
    private HotelsPostsService hotelsPostService;

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<HotelsPostDTO>>> findHotelsByQuery(@RequestBody QueryCreatedDTO queryCreatedDTO) {
        return ResponseEntity.ok().body(new ApiResponse<>(hotelsPostService.findHotelsByQuery(queryCreatedDTO)
                , "Success", new Date()));
    }

    @PreAuthorize("hasRole('ROLE_HOTEL')")
    @PostMapping("/create-post/{userId}")
    public ResponseEntity<ApiResponse<HotelsPostDTO>> hotelPost(@Valid @PathVariable("userId") Integer userId,
                                                                @RequestBody PostCreatedDTO postCreatedDTO) {
        return ResponseEntity.ok().body(new ApiResponse<>(hotelsPostService.createPost(userId, postCreatedDTO)
                , "Success", new Date()));
    }

    @GetMapping("/fetch-posts")
    public ResponseEntity<ApiResponse<List<HotelsPostDTO>>> findAllHotelsPost() {
        return ResponseEntity.ok().body(new ApiResponse<>(hotelsPostService.findAllHotelsPost()
                , "Success", new Date()));
    }

    @GetMapping("/fetch-one-post/{hotelsPostId}")
    public ResponseEntity<ApiResponse<HotelsPostDTO>> findByIdHotelsPost(@PathVariable("hotelsPostId") String hotelsPostId) {
        return ResponseEntity.ok().body(new ApiResponse<>(hotelsPostService.getHotelsPostById(hotelsPostId)
                , "Success", new Date()));
    }

    @GetMapping("/fetch-one-details-post/{hotelDetailsPostId}")
    public ResponseEntity<ApiResponse<HotelsPostDTO>> getHotelsDetailsPostById(@PathVariable("hotelDetailsPostId") String hotelsPostId) {
        return ResponseEntity.ok().body(new ApiResponse<>(hotelsPostService.getHotelsDetailsPostById(hotelsPostId)
                , "Success", new Date()));
    }


    @PreAuthorize("hasRole('ROLE_HOTEL')")
    @PutMapping("/update-post/{hotelsPostId}")
    public ResponseEntity<ApiResponse<HotelsPostDTO>> updateHotelsPost(@PathVariable("hotelsPostId") String hotelsPostId,
                                                                       @Valid @RequestBody PostCreatedDTO postCreatedDTO) {
        return ResponseEntity.ok().body(new ApiResponse<>(hotelsPostService.updateHotelsPost(hotelsPostId, postCreatedDTO)
                , "Success", new Date()));
    }

    @PreAuthorize("hasRole('ROLE_HOTEL')")
    @DeleteMapping("/delete-post/{hotelsPostId}")
    public ResponseEntity<ApiResponse<String>> deleteHotelsPost(@PathVariable("hotelsPostId") String hotelsPostId) {
        return ResponseEntity.ok().body(new ApiResponse<>(hotelsPostService.deleteHotelsPost(hotelsPostId),
                "Post successfully deleted", new Date()));
    }
}
