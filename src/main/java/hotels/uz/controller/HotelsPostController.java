package hotels.uz.controller;

import hotels.uz.dto.Auth.ApplicationData;
import hotels.uz.dto.hotels.created.PostCreatedDTO;
import hotels.uz.dto.hotels.dto.PostDTO;
import hotels.uz.service.hotels.HotelsPostsService;
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
@Tag(name = "Hotels Post Controller", description = "User can post")
public class HotelsPostController {

    @Autowired
    private HotelsPostsService hotelsPostService;

    @PreAuthorize("hasRole('HOTEL_ROLE')")
    @PostMapping("/create_post/{userId}")
    public ResponseEntity<ApplicationData<PostDTO>> hotelPost(@Valid @PathVariable("userId") Integer userId,
                                                              @RequestBody PostCreatedDTO postCreatedDTO) {
        return ResponseEntity.ok().body(new ApplicationData<>(hotelsPostService.createPost(userId, postCreatedDTO)
                , "Success", new Date()));
    }

    @GetMapping("/fetch_posts")
    public ResponseEntity<ApplicationData<List<PostDTO>>> findAllHotelsPost() {
        return ResponseEntity.ok().body(new ApplicationData<>(hotelsPostService.findAllHotelsPost()
                , "Success", new Date()));
    }

    @GetMapping("/fetch_one_post/{hotelsPostId}")
    public ResponseEntity<ApplicationData<PostDTO>> findByIdHotelsPost(@PathVariable("hotelsPostId") String hotelsPostId) {
        return ResponseEntity.ok().body(new ApplicationData<>(hotelsPostService.getHotelsPostById(hotelsPostId)
                , "Success", new Date()));
    }


    @PreAuthorize("hasRole('HOTEL_ROLE')")
    @PutMapping("/update_post/{hotelsPostId}")
    public ResponseEntity<ApplicationData<PostDTO>> updateHotelsPost(@PathVariable("hotelsPostId") String hotelsPostId,
                                                                     @Valid @RequestBody PostCreatedDTO postCreatedDTO) {
        return ResponseEntity.ok().body(new ApplicationData<>(hotelsPostService.updateHotelsPost(hotelsPostId, postCreatedDTO)
                , "Success", new Date()));
    }

    @PreAuthorize("hasRole('HOTEL_ROLE')")
    @DeleteMapping("/delete_post/{hotelsPostId}")
    public ResponseEntity<ApplicationData<String>> deleteHotelsPost(@PathVariable("hotelsPostId") String hotelsPostId) {
        return ResponseEntity.ok().body(new ApplicationData<>(hotelsPostService.deleteHotelsPost(hotelsPostId),
                "Post successfully deleted", new Date()));
    }
}
