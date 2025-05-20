package hotels.uz.service.hotels.taxi;

import hotels.uz.dto.hotels.dto.hotel.taxi.TaxiImageDTO;
import hotels.uz.entity.hotels.taxi.TaxiImageEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.exceptions.AppBadException;
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
public class TaxiImageService {

    @Autowired
    private TaxiImageRepository taxiImageRepository;
    @Value("${attach.upload.folder}")
    private String folderName;
    @Value("${attach.upload.url}")
    private String url;

    public TaxiImageDTO uploadTaxiImage(MultipartFile file, Integer userId) {
        if (SpringSecurityUtil.hasRole(ProfileRole.ROLE_ADMIN) &&
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
                TaxiImageEntity entity = new TaxiImageEntity();
                entity.setTaxiImageId(keyUUID);
                entity.setPath(pathFolder);
                entity.setExtension(extension);
                entity.setOriginalName(file.getOriginalFilename());
                entity.setSize(file.getSize());
                taxiImageRepository.save(entity);
                return toDTO(entity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public ResponseEntity<Resource> downloadTaxiImage(String taxiImageId) {
        TaxiImageEntity entity = getById(taxiImageId);
        Path filePath = Paths.get(folderName + "/" + entity.getPath() +
                "/" + entity.getTaxiImageId() + "." + entity.getExtension()).normalize();
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

    public String deleteTaxiImage(String taxiImageId) {
        String removeTaxiImageId = removeByExtension(taxiImageId);
        Optional<TaxiImageEntity> optional = taxiImageRepository.findById(removeTaxiImageId);
        if (optional.isEmpty()) {
            throw new AppBadException("Photo is not found!");
        }
        TaxiImageEntity entity = optional.get();
        String fullPath = folderName + "/" + entity.getPath() + "/" + entity.getTaxiImageId() + "." + entity.getExtension();
        File file = new File(fullPath);
        if (file.exists()) {
            file.delete();
        }
        taxiImageRepository.deleteById(removeTaxiImageId);
        return "Deleted: " + taxiImageId;
    }

    public String removeByExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return (dotIndex > 0) ? fileName.substring(0, dotIndex) : fileName;
    }

    public TaxiImageEntity getById(String taxiImageId) {
        Optional<TaxiImageEntity> optional = taxiImageRepository.findById(taxiImageId);
        if (optional.isEmpty()) {
            throw new AppBadException("Photo is not found!");
        }
        return optional.get();
    }

    public TaxiImageDTO toDTO(TaxiImageEntity entity) {
        TaxiImageDTO dto = new TaxiImageDTO();
        dto.setTaxiImageId(entity.getTaxiImageId());
        dto.setOrigenName(entity.getOriginalName());
        dto.setExtension(entity.getExtension());
        dto.setSize(entity.getSize());
        dto.setUrl(url + "/download/" + entity.getTaxiImageId() + "." + entity.getExtension());
        return dto;
    }

    public TaxiImageDTO taxiImageDTO(String taxiImageId) {
        if (taxiImageId == null) return null;
        TaxiImageDTO dto = new TaxiImageDTO();
        dto.setTaxiImageId(taxiImageId);
        dto.setUrl(url + "/download/" + taxiImageId);
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
