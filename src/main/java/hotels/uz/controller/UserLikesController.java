package hotels.uz.controller;

import hotels.uz.service.hotels.likes.UserLikesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like")
@Tag(name = "User likes Controller", description = "User can like and unlike posts")
public class UserLikesController {

    @Autowired
    private UserLikesService userLikesService;

    @PostMapping("/isLiked/{postId}/{userId}")
    public ResponseEntity<String> isLiked(@PathVariable String postId, @PathVariable Integer userId) {
        boolean isLiked = userLikesService.isLiked(postId, userId);
        return ResponseEntity.ok(isLiked ? "Post liked" : "Post unliked");
    }

    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> getLikesCount(@PathVariable String postId) {
        return ResponseEntity.ok(userLikesService.getLikeCount(postId));
    }
}
