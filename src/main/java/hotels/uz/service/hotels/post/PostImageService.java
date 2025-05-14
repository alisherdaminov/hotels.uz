package hotels.uz.service.hotels.post;

import hotels.uz.dto.hotels.dto.PostHotelDetailsImageDTO;
import hotels.uz.dto.hotels.dto.PostRegionImageDTO;
import hotels.uz.entity.hotels.PostImageEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.exceptions.AppBadException;
import hotels.uz.repository.hotels.PostImageRepository;
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
import java.util.*;

@Service
public class PostImageService {

    @Autowired
    private PostImageRepository postImageRepository;
    @Value("${attach.upload.folder}")
    private String folderName;
    @Value("${attach.upload.url}")
    private String url;

    public PostRegionImageDTO uploadRegionImage(MultipartFile file, Integer userId) {
        if (SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE) &&
                userId.equals(SpringSecurityUtil.getCurrentUserId())) {
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
                PostImageEntity entity = new PostImageEntity();
                entity.setPostImageId(keyUUID);
                entity.setPath(pathFolder);
                entity.setExtension(extension);
                entity.setOriginalName(file.getOriginalFilename());
                entity.setSize(file.getSize());
                postImageRepository.save(entity);
                return toDTO(entity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public ResponseEntity<Resource> downloadRegionImage(String postImageId) {
        PostImageEntity entity = getById(postImageId);
        Path filePath = Paths.get(folderName + "/" + entity.getPath() +
                "/" + entity.getPostImageId() +
                "." + entity.getExtension()).normalize();
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new AppBadException("Photo not found: " + postImageId);
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

    public PostRegionImageDTO uploadHotelDetailsImage(MultipartFile file, Integer userId) {
        if (SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE) &&
                userId.equals(SpringSecurityUtil.getCurrentUserId())) {
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
                PostImageEntity entity = new PostImageEntity();
                entity.setPostImageId(keyUUID);
                entity.setPath(pathFolder);
                entity.setExtension(extension);
                entity.setOriginalName(file.getOriginalFilename());
                entity.setSize(file.getSize());
                postImageRepository.save(entity);
                return toDTO(entity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public ResponseEntity<Resource> downloadHotelDetailsImage(String postImageId) {
        PostImageEntity entity = getById(postImageId);
        Path filePath = Paths.get(folderName + "/" + entity.getPath() +
                "/" + entity.getPostImageId() +
                "." + entity.getExtension()).normalize();
        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new AppBadException("Photo not found: " + postImageId);
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

    public String deletePostById(String postImageId) {
        String postImageByRemoved = removeByExtension(postImageId);
        Optional<PostImageEntity> optional = postImageRepository.findById(postImageByRemoved);
        if (optional.isEmpty()) {
            throw new AppBadException("Not found: " + postImageByRemoved);
        }
        PostImageEntity entity = optional.get();
        String fullPath = folderName + "/" + entity.getPath() + "/" + entity.getPostImageId() + "." + entity.getExtension();
        File file = new File(fullPath);
        if (file.exists()) {
            file.delete();
        }
        postImageRepository.deleteById(postImageByRemoved);
        return "Deleted: " + postImageByRemoved;
    }

    private String removeByExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return (dotIndex > 0) ? fileName.substring(0, dotIndex) : fileName;
    }

    public PostImageEntity getById(String postImageId) {
        Optional<PostImageEntity> optional = postImageRepository.findById(postImageId);
        if (optional.isEmpty()) {
            throw new AppBadException("Photo is not found!");
        }
        return optional.get();
    }

    public PostRegionImageDTO toDTO(PostImageEntity entity) {
        PostRegionImageDTO dto = new PostRegionImageDTO();
        dto.setPostRegionImageId(entity.getPostImageId());
        dto.setOrigenName(entity.getOriginalName());
        dto.setExtension(entity.getExtension());
        dto.setSize(entity.getSize());
        dto.setUrl(url + "/download/" + entity.getPostImageId() + "." + entity.getExtension());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    // for sending images while logging
    public PostRegionImageDTO postImageDTO(String postImageId) {
        if (postImageId == null) return null;
        PostRegionImageDTO dto = new PostRegionImageDTO();
        dto.setPostRegionImageId(postImageId);
        dto.setUrl(url + "/download/" + postImageId);
        return dto;
    }
    // for sending images while logging
    public PostHotelDetailsImageDTO postImageListDTO(String postImageId) {
        if (postImageId == null) return null;
        PostHotelDetailsImageDTO dto = new PostHotelDetailsImageDTO();
        dto.setPostHotelDetailsImageId(postImageId);
        dto.setUrl(url + "/download/" + postImageId);
        return dto;
    }

    public String getDateString() {
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
