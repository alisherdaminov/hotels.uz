package hotels.uz.service.hotels.adverts;

import hotels.uz.dto.hotels.created.hotel.adverts.ShortAdvertsCreatedDTO;
import hotels.uz.dto.hotels.dto.hotel.adverts.ShortAdvertsDTO;
import hotels.uz.entity.hotels.adverts.ShortAdvertsEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.exceptions.AppBadException;
import hotels.uz.repository.hotels.adverts.ShortAdvertsRepository;
import hotels.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShortAdvertsService {

    @Autowired
    private ShortAdvertsRepository shortAdvertsRepository;

    //CREATE
    public ShortAdvertsDTO createShortAdverts(ShortAdvertsCreatedDTO advertsCreatedDTO, Integer userId) {
        ShortAdvertsEntity entity = new ShortAdvertsEntity();
        boolean isExistExpirationDate = shortAdvertsRepository.isExistExpirationDateInAdverts(LocalDateTime.now());
        if (isExistExpirationDate) {
            throw new AppBadException("Adverts already exists, Please try again later");
        } else if (SpringSecurityUtil.hasRole(ProfileRole.ROLE_HOTEL) && userId.equals(SpringSecurityUtil.getCurrentUserId())) {
            entity.setTitle(advertsCreatedDTO.getTitle());
            entity.setStayDescription(advertsCreatedDTO.getStayDescription());
            entity.setDiscountDescription(advertsCreatedDTO.getDiscountDescription());
            entity.setUserId(SpringSecurityUtil.getCurrentUserId());
            entity.setCardNumber(advertsCreatedDTO.getCardNumber());
            entity.setCardCvv(advertsCreatedDTO.getCardCvv());
            entity.setAmountOfMoney(advertsCreatedDTO.getAmountOfMoney());
            entity.setCreatedDate(LocalDateTime.now());
            entity.setAdvertsExpirationDate(LocalDateTime.now().plusDays(10));
            shortAdvertsRepository.save(entity);
        }
        return toDTO(entity);
    }


    //GET ALL POSTS
    public ShortAdvertsDTO findShortAdverts(String shortAdvertsId) {
        ShortAdvertsEntity shortAdvertsEntity = shortAdvertsRepository.findByShortAdvertsId(shortAdvertsId);
        return toDTO(shortAdvertsEntity);
    }

    // UPDATE BY ID
    public ShortAdvertsDTO updateShortAdverts(ShortAdvertsCreatedDTO advertsCreatedDTO, String advertsId) {
        ShortAdvertsEntity entity = getById(advertsId);
        boolean isExist = shortAdvertsRepository.isExistExpirationDateInAdverts(LocalDateTime.now());
        if (isExist && SpringSecurityUtil.hasRole(ProfileRole.ROLE_HOTEL)) {
            String title = advertsCreatedDTO.getTitle();
            String stayDesc = advertsCreatedDTO.getStayDescription();
            String discountDesc = advertsCreatedDTO.getDiscountDescription();
            shortAdvertsRepository.updateShortAdverts(title, stayDesc, discountDesc, advertsId);
        }
        return toDTO(entity);
    }


    //DELETE BY ID
    public String deleteShortAdverts(String advertsId) {
        boolean isExist = shortAdvertsRepository.isExistExpirationDateInAdverts(LocalDateTime.now());
        if (isExist || SpringSecurityUtil.hasRole(ProfileRole.ROLE_HOTEL)) {
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
        dto.setCardNumber(entity.getCardNumber());
        dto.setCardCvv(entity.getCardCvv());
        dto.setAmountOfMoney(entity.getAmountOfMoney());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setAdvertsExpirationDate(LocalDateTime.now().plusDays(10));
        return dto;
    }

    //GET ADVERTS BY ID
    public ShortAdvertsEntity getById(String advertId) {
        return shortAdvertsRepository.findById(advertId).orElseThrow(() -> new RuntimeException("Advert ID not found: " + advertId));
    }
}


