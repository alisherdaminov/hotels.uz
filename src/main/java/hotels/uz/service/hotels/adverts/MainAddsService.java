package hotels.uz.service.hotels.adverts;

import hotels.uz.dto.hotels.dto.hotel.adverts.MainAddsDTO;
import hotels.uz.entity.hotels.adverts.MainAddsEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.exceptions.AppBadException;
import hotels.uz.repository.hotels.adverts.MainAddsRepository;
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
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class MainAddsService {

    @Autowired
    private MainAddsRepository mainAddsRepository;

    @Value("${attach.upload.folder}")
    private String folderName;
    @Value("${attach.upload.url}")
    private String url;


    // UPLOAD IMAGE
    public MainAddsDTO uploadImage(MultipartFile file, Integer userId) {
        boolean isExistExpirationDate = mainAddsRepository.isExistExpirationDate(LocalDateTime.now());
        if (isExistExpirationDate) {
            throw new AppBadException("Adds already exists, Please try again later");
        } else if (SpringSecurityUtil.hasRole(ProfileRole.ROLE_HOTEL) &&
                userId.equals(SpringSecurityUtil.getCurrentUserId())) {
            if (file.isEmpty()) {
                throw new AppBadException("Photo is not found");
            }
            try {
                String pathFolder = getYmDString();
                String keyUUID = UUID.randomUUID().toString();
                String extension = getExtension(Objects.requireNonNull(file.getOriginalFilename()));
                File folder = new File(folderName + "/" + pathFolder);
                if (!folder.exists()) {
                    boolean result = folder.mkdirs();
                }
                byte[] bytes = file.getBytes();
                Path path = Paths.get(folderName + "/" + pathFolder + "/" + keyUUID + "." + extension);
                Files.write(path, bytes);
                MainAddsEntity entity = new MainAddsEntity();
                entity.setPhotoId(keyUUID);
                entity.setPath(pathFolder);
                entity.setExtension(extension);
                entity.setOriginalName(file.getOriginalFilename());
                entity.setSize(file.getSize());
                mainAddsRepository.save(entity);
                return toDTO(entity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    //DOWNLOAD IMAGE
    public ResponseEntity<Resource> downloadImage(String photoId) {
        MainAddsEntity entity = getById(photoId);
        Path filePath = Paths.get(folderName + "/" + entity.getPath() + "/"
                + entity.getPhotoId() + "." + entity.getExtension()).normalize();
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

    public String deleteAdds(String photoIdWithExtension) {
        String photoId = removeExtension(photoIdWithExtension);
        boolean isExistExpirationDate = mainAddsRepository.isExistExpirationDate(LocalDateTime.now());
        if (!isExistExpirationDate && !SpringSecurityUtil.hasRole(ProfileRole.ROLE_USER)) {
            throw new AppBadException("You are not allowed to delete this ad.");
        }
        Optional<MainAddsEntity> optional = mainAddsRepository.findById(photoId);
        if (optional.isEmpty()) {
            throw new AppBadException("Not found: " + photoId);
        }
        MainAddsEntity entity = optional.get();
        String fullPath = folderName + "/" + entity.getPath() + "/" + entity.getPhotoId() + "." + entity.getExtension();
        File file = new File(fullPath);
        if (file.exists()) {
            file.delete();
        }
        mainAddsRepository.deleteById(photoId);
        return "Deleted " + photoId;
    }

    private String removeExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex > 0) ? fileName.substring(0, dotIndex) : fileName;
    }


    // GET BY ID
    public MainAddsEntity getById(String photoId) {
        Optional<MainAddsEntity> optional = mainAddsRepository.findById(photoId);
        if (optional.isEmpty()) {
            throw new AppBadException("File not found!");
        }
        return optional.get();
    }

    public MainAddsDTO toDTO(MainAddsEntity entity) {
        MainAddsDTO dto = new MainAddsDTO();
        dto.setPhotoId(entity.getPhotoId());
        dto.setOrigenName(entity.getOriginalName());
        dto.setExtension(entity.getExtension());
        dto.setSize(entity.getSize());
        dto.setUrl(url + "/download/" + entity.getPhotoId() + "." + entity.getExtension());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    // this is for sending images while logging
    public MainAddsDTO mainAddsDTO(String photoId) {
        if (photoId == null) return null;
        MainAddsDTO dto = new MainAddsDTO();
        dto.setPhotoId(photoId);
        dto.setUrl(url + "/" + "download" + "/" + photoId);
        return dto;
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }

    public String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }
}
