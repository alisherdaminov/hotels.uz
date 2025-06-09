package hotels.uz.controller;

import hotels.uz.dto.Auth.ApiResponse;
import hotels.uz.dto.Auth.ResponseDTO;
import hotels.uz.dto.Auth.UserCreatedDTO;
import hotels.uz.dto.Auth.profile.UserProfileImageDTO;
import hotels.uz.service.auth.AuthService;
import hotels.uz.service.auth.profile.UserProfileImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/user-profile-image")
@Tag(name = "User Profile Image",description = "User Profile Image can be uploaded and downloaded, updated and deleted")
public class UserProfileImage {

    @Autowired
    private UserProfileImageService userProfileImageService;
    @Autowired
    private AuthService authService;

    @PostMapping("/upload/{userId}")
    public ResponseEntity<ApiResponse<UserProfileImageDTO>> uploadRegionImage(@RequestParam("file") MultipartFile file,
                                                                              @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(new ApiResponse<>(userProfileImageService.uploadImage(file, userId),
                "Success", new Date()));
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadRegionImage(@PathVariable("filename") String filename) {
        return userProfileImageService.downloadImage(filename);
    }

    @PutMapping("/update-user-details/{userId}")
    public ResponseEntity<ApiResponse<ResponseDTO>> updateUserDetails(@PathVariable("userId") Integer userId,
                                                                      @RequestBody UserCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new ApiResponse<>(authService.updateUserDetails(userId, createdDTO),
                "Success", new Date()));
    }

    @DeleteMapping("/delete/{photoId}")
    public ResponseEntity<ApiResponse<String>> deleteAdds(@PathVariable("photoId") String photoId) {
        return ResponseEntity.ok().body(new ApiResponse<>(userProfileImageService.deleteUserImage(photoId),
                "Success", new Date()));
    }
}
