package hotels.uz.service.hotels;

import hotels.uz.dto.hotels.created.MainAddsCreatedDTO;
import hotels.uz.dto.hotels.dto.MainAddsDTO;
import hotels.uz.entity.hotels.MainAddsEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.exceptions.AppBadException;
import hotels.uz.repository.hotels.MainAddsRepository;
import hotels.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MainAddsService {

    @Autowired
    private MainAddsRepository mainAddsRepository;


    //CREATE
    public MainAddsDTO createAdds(MainAddsCreatedDTO dto, Integer userId) {
        MainAddsEntity entity = new MainAddsEntity();
        boolean isExistExpirationDate = mainAddsRepository.isExistExpirationDate(LocalDateTime.now());
        if (isExistExpirationDate) {
            throw new AppBadException("Adds already exists, Please try again later");
        } else if (SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE)
                && userId.equals(SpringSecurityUtil.getCurrentUserId())) {
            entity.setTitle(dto.getTitle());
            entity.setDescription(dto.getDescription());
            //  entity.setImage(dto.getImage());
            entity.setCreatedDate(LocalDateTime.now());
            mainAddsRepository.save(entity);
        }
        return toDTO(entity);
    }

    //GET
    public MainAddsDTO findAddsById(String mainAddsId) {
        MainAddsEntity entity = mainAddsRepository.findByMainAddsId(mainAddsId);
        return toDTO(entity);
    }

    //DELETE
    public String deleteAdds(String mainAddsId) {
        boolean isExistExpirationDate = mainAddsRepository.isExistExpirationDate(LocalDateTime.now());
        if (!isExistExpirationDate || !SpringSecurityUtil.hasRole(ProfileRole.USER_ROLE)) {
            mainAddsRepository.delete(getById(mainAddsId));
        }
        return "Deleted";
    }


    public MainAddsDTO toDTO(MainAddsEntity entity) {
        MainAddsDTO dto = new MainAddsDTO();
        dto.setMainAddsId(entity.getMainAddsId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        // dto.setImage(entity.getImage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }


    public MainAddsEntity getById(String mainAddsId) {
        return mainAddsRepository.findById(mainAddsId).orElseThrow(() -> new RuntimeException("Adds ID not found: " + mainAddsId));
    }

}
