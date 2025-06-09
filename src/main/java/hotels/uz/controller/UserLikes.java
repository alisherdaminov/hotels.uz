package hotels.uz.controller;

import hotels.uz.dto.Auth.ApiResponse;
import hotels.uz.dto.hotels.dto.likes.UserLikesDTO;
import hotels.uz.service.hotels.likes.UserLikesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/like")
@Tag(name = "User likes ", description = "User can like and unlike posts of hotels")
public class UserLikes {

    @Autowired
    private UserLikesService userLikesService;

    @PostMapping("/isLiked/{postId}/{userId}")
    public ResponseEntity<ApiResponse<UserLikesDTO>> isLiked(@PathVariable("postId") String postId,
                                                             @PathVariable("userId") Integer userId) {
        UserLikesDTO isLiked = userLikesService.isLiked(postId, userId);
        String message = isLiked.isLiked() ? "Liked" : "Unliked";
        ApiResponse<UserLikesDTO> response = new ApiResponse(message, "Success", new Date());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/counts")
    public ResponseEntity<ApiResponse<List<UserLikesDTO>>> getLikesAllCounts() {
        return ResponseEntity.ok(new ApiResponse<>(userLikesService.findAllLikes(),
                "Success", new Date()));
    }
}
