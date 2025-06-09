package hotels.uz.controller;

import hotels.uz.dto.Auth.ApiResponse;
import hotels.uz.dto.hotels.dto.hotel.post.PostRegionImageDTO;
import hotels.uz.service.hotels.post.PostImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/post-image")
@Tag(name = "Post Image", description = "Post Image can be uploaded and downloaded")
public class PostImage {

    @Autowired
    private PostImageService postImageService;

    @PostMapping("/upload-region/{userId}")
    public ResponseEntity<ApiResponse<PostRegionImageDTO>> uploadRegionImage(@RequestParam("file") MultipartFile file,
                                                                             @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(new ApiResponse<>(postImageService.uploadRegionImage(file, userId),
                "Success", new Date()));
    }

    @GetMapping("/download-region/{filename}")
    public ResponseEntity<Resource> downloadRegionImage(@PathVariable("filename") String filename) {
        return postImageService.downloadRegionImage(filename);
    }

    @PostMapping("/upload-hotel-details/{userId}")
    public ResponseEntity<ApiResponse<PostRegionImageDTO>> uploadHotelDetailsImage(@RequestParam("file") MultipartFile file,
                                                                                   @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(new ApiResponse<>(postImageService.uploadHotelDetailsImage(file, userId),
                "Success", new Date()));
    }

    @GetMapping("/download-hotel-details/{filename}")
    public ResponseEntity<Resource> downloadHotelDetailsImage(@PathVariable("filename") String filename) {
        return postImageService.downloadHotelDetailsImage(filename);
    }

    @DeleteMapping("/delete/{photoId}")
    public ResponseEntity<ApiResponse<String>> deleteAdds(@PathVariable("photoId") String photoId) {
        return ResponseEntity.ok().body(new ApiResponse<>(postImageService.deletePostById(photoId),
                "Success", new Date()));
    }
}
