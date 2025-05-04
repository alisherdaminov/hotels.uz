package hotels.uz.service.hotels;

import hotels.uz.dto.hotels.created.ShortAdvertsCreatedDTO;
import hotels.uz.dto.hotels.dto.ShortAdvertsDTO;
import hotels.uz.entity.hotels.ShortAdvertsEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.repository.hotels.ShortAdvertsRepository;
import hotels.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ShortAdvertsService {

    @Autowired
    private ShortAdvertsRepository shortAdvertsRepository;

    //CREATE
    public ShortAdvertsDTO createShortAdverts(ShortAdvertsCreatedDTO advertsCreatedDTO, Integer userId) {
        ShortAdvertsEntity entity = new ShortAdvertsEntity();
        if (SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE) && userId.equals(SpringSecurityUtil.getCurrentUserId())) {
            entity.setTitle(advertsCreatedDTO.getTitle());
            entity.setStayDescription(advertsCreatedDTO.getStayDescription());
            entity.setDiscountDescription(advertsCreatedDTO.getDiscountDescription());
            entity.setUserId(SpringSecurityUtil.getCurrentUserId());
            shortAdvertsRepository.save(entity);
        }
        return toDTO(entity);
    }

    //GET ALL POSTS
    public List<ShortAdvertsDTO> findAllShortAdverts() {
        List<ShortAdvertsEntity> shortAdvertsEntityList = shortAdvertsRepository.findAll();
        List<ShortAdvertsDTO> shortAdvertsDTOList = new LinkedList<>();
        for (ShortAdvertsEntity entity : shortAdvertsEntityList) {
            shortAdvertsDTOList.add(toDTO(entity));
        }
        return shortAdvertsDTOList;
    }

    // UPDATE BY ID
    public ShortAdvertsDTO updateShortAdverts(ShortAdvertsCreatedDTO advertsCreatedDTO, String advertsId) {
        ShortAdvertsEntity entity = getById(advertsId);
        if (SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE)) {
            String title = advertsCreatedDTO.getTitle();
            String stayDesc = advertsCreatedDTO.getStayDescription();
            String discountDesc = advertsCreatedDTO.getDiscountDescription();
            shortAdvertsRepository.updateShortAdverts(title, stayDesc, discountDesc, advertsId);
        }
        return toDTO(entity);
    }


    //DELETE BY ID
    public String deleteShortAdverts(String advertsId) {
        if (SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE)) {
            shortAdvertsRepository.delete(getById(advertsId));
        }
        return "Deleted";
    }

    //TO DTO
    public ShortAdvertsDTO toDTO(ShortAdvertsEntity entity) {
        ShortAdvertsDTO dto = new ShortAdvertsDTO();
        dto.setShortAdvertsId(entity.getShortAdvertsId());
        dto.setTitle(entity.getTitle());
        dto.setStayDescription(entity.getStayDescription());
        dto.setDiscountDescription(entity.getDiscountDescription());
        return dto;
    }

    //GET ADVERTS BY ID
    public ShortAdvertsEntity getById(String advertId) {
        return shortAdvertsRepository.findById(advertId).orElseThrow(() -> new RuntimeException("Advert ID not found: " + advertId));
    }
}
