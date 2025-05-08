package hotels.uz.controller;

import hotels.uz.dto.Auth.ApplicationData;
import hotels.uz.dto.hotels.dto.MainAddsDTO;
import hotels.uz.service.hotels.MainAddsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/main_adds")
public class MainAddsController {

    @Autowired
    private MainAddsService mainAddsService;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<ApplicationData<MainAddsDTO>> uploadImage(@RequestParam("file") MultipartFile file,
                                                                    @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(new ApplicationData<>(mainAddsService.uploadImage(file, userId),
                "Success", new Date()));
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("filename") String filename) {
        return mainAddsService.downloadImage(filename);
    }

    @DeleteMapping("/delete/{photoId}")
    public ResponseEntity<ApplicationData<String>> deleteAdds(@PathVariable("photoId") String photoId) {
        return ResponseEntity.ok().body(new ApplicationData<>(mainAddsService.deleteAdds(photoId),
                "Success", new Date()));
    }
}
