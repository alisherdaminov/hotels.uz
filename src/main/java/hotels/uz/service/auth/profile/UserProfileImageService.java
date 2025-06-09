package hotels.uz.service.auth.profile;

import hotels.uz.dto.Auth.profile.UserProfileImageDTO;
import hotels.uz.entity.auth.profile.UserProfileImageEntity;
import hotels.uz.exceptions.AppBadException;
import hotels.uz.repository.auth.UserProfileImageRepository;
import hotels.uz.repository.hotels.taxi.TaxiImageRepository;
import hotels.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserProfileImageService {

    @Autowired
    private UserProfileImageRepository repository;

    @Autowired
    private TaxiImageRepository taxiImageRepository;
    @Value("${attach.upload.folder}")
    private String folderName;
    @Value("${attach.upload.url}")
    private String url;

    public UserProfileImageDTO uploadImage(MultipartFile file, Integer userId) {
        if (userId.equals(SpringSecurityUtil.getCurrentUserId())) {
            if (file.isEmpty()) {
                throw new AppBadException("Photo is not found!");
            }
            try {
                String pathFolder = getDateString();
                String keyUUID = UUID.randomUUID().toString();
                String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
                File folder = new File(folderName + "/" + pathFolder);
                if (!folder.exists()) {
                    boolean result = folder.mkdirs();
                }
                Path path = Paths.get(folderName + "/" + pathFolder + "/" + keyUUID + "." + extension);
                byte[] bytes = file.getBytes();
                Files.write(path, bytes);
                UserProfileImageEntity entity = new UserProfileImageEntity();
                entity.setUserProfileImageId(keyUUID);
                entity.setPath(pathFolder);
                entity.setExtension(extension);
                entity.setOriginalName(file.getOriginalFilename());
                entity.setSize(file.getSize());
                repository.save(entity);
                return toDTO(entity);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }

    public ResponseEntity<Resource> downloadImage(String userImageId) {
        UserProfileImageEntity entity = getById(userImageId);
        Path filePath = Paths.get(folderName + "/" + entity.getPath() +
                "/" + entity.getUserProfileImageId() + "." + entity.getExtension()).normalize();
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new AppBadException("Photo is not found!");
            }
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String deleteUserImage(String userImageId) {
        String removedUserImageId = removeUserImage(userImageId);
        Optional<UserProfileImageEntity> optional = repository.findById(removedUserImageId);
        if (optional.isEmpty()) {
            throw new AppBadException("Photo is not found!");
        }
        UserProfileImageEntity user = optional.get();
        String fullPath = folderName + "/" + user.getPath() + "/" + user.getUserProfileImageId() + "." + user.getExtension();
        File file = new File(fullPath);
        if (file.exists()) {
            file.delete();
        }
        repository.deleteById(removedUserImageId);
        return "Deleted: " + removedUserImageId;
    }

    public String removeUserImage(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        return (dotIndex > 0) ? filename.substring(0, dotIndex) : filename;
    }

    public UserProfileImageEntity getById(String userImageId) {
        Optional<UserProfileImageEntity> optional = repository.findById(userImageId);
        if (optional.isEmpty()) {
            throw new AppBadException("Photo is not found!");
        }
        return optional.get();
    }

    public UserProfileImageDTO toDTO(UserProfileImageEntity entity) {
        UserProfileImageDTO dto = new UserProfileImageDTO();
        dto.setUserProfileImageId(entity.getUserProfileImageId());
        dto.setOrigenName(entity.getOriginalName());
        dto.setExtension(entity.getExtension());
        dto.setSize(entity.getSize());
        dto.setUrl(url + "/download/" + entity.getUserProfileImageId() + "." + entity.getExtension());
        return dto;
    }

    public UserProfileImageDTO userImage(String userImageId) {
        if (userImageId == null) return null;
        UserProfileImageDTO dto = new UserProfileImageDTO();
        dto.setUserProfileImageId(userImageId);
        dto.setUrl(url + "/download/" + userImageId);
        return dto;
    }

    public String getDateString() {
        Calendar calendar = Calendar.getInstance();
        return String.valueOf(calendar.get(Calendar.YEAR))
                + calendar.get(Calendar.MONTH)
                + calendar.get(Calendar.DAY_OF_MONTH);
    }

    public String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }
}
