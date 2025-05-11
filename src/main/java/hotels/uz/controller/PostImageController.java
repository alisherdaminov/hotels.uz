package hotels.uz.controller;

import hotels.uz.dto.Auth.ApiResponse;
import hotels.uz.dto.hotels.dto.MainAddsDTO;
import hotels.uz.dto.hotels.dto.PostImageDTO;
import hotels.uz.service.hotels.MainAddsService;
import hotels.uz.service.hotels.post.PostImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/post_image")
public class PostImageController {

    @Autowired
    private PostImageService postImageService;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<ApiResponse<PostImageDTO>> uploadImage(@RequestParam("file") MultipartFile file,
                                                                 @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(new ApiResponse<>(postImageService.uploadPostImage(file, userId),
                "Success", new Date()));
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("filename") String filename) {
        return postImageService.downloadPostImage(filename);
    }

    @DeleteMapping("/delete/{photoId}")
    public ResponseEntity<ApiResponse<String>> deleteAdds(@PathVariable("photoId") String photoId) {
        return ResponseEntity.ok().body(new ApiResponse<>(postImageService.deletePostById(photoId),
                "Success", new Date()));
    }
}
