package hotels.uz.controller;

import hotels.uz.dto.Auth.ApiResponse;
import hotels.uz.dto.hotels.dto.hotel.adverts.MainAddsDTO;
import hotels.uz.service.hotels.adverts.MainAddsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/main-adds")
@Tag(name = "MainAdds", description = "MainAdds for Hotels to announce their services in the main page")
public class MainAdds {

    @Autowired
    private MainAddsService mainAddsService;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<ApiResponse<MainAddsDTO>> uploadImage(@RequestParam("file") MultipartFile file,
                                                                @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(new ApiResponse<>(mainAddsService.uploadImage(file, userId),
                "Success", new Date()));
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("filename") String filename) {
        return mainAddsService.downloadImage(filename);
    }

    @DeleteMapping("/delete/{photoId}")
    public ResponseEntity<ApiResponse<String>> deleteAdds(@PathVariable("photoId") String photoId) {
        return ResponseEntity.ok().body(new ApiResponse<>(mainAddsService.deleteAdds(photoId),
                "Success", new Date()));
    }
}
