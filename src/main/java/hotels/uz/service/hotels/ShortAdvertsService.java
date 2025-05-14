package hotels.uz.service.hotels;

import hotels.uz.dto.hotels.created.*;
import hotels.uz.dto.hotels.dto.HotelsConditionDTO;
import hotels.uz.dto.hotels.dto.HotelsDetailsDTO;
import hotels.uz.dto.hotels.dto.HotelsPostDTO;
import hotels.uz.dto.hotels.dto.ShortAdvertsDTO;
import hotels.uz.entity.hotels.HotelsConditionEntity;
import hotels.uz.entity.hotels.HotelsDetailsEntity;
import hotels.uz.entity.hotels.HotelsEntity;
import hotels.uz.entity.hotels.ShortAdvertsEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.exceptions.AppBadException;
import hotels.uz.repository.auth.UserRepository;
import hotels.uz.repository.hotels.*;
import hotels.uz.service.hotels.likes.UserLikesService;
import hotels.uz.service.hotels.post.BookingService;
import hotels.uz.service.hotels.post.PostImageService;
import hotels.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        } else if (SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE) && userId.equals(SpringSecurityUtil.getCurrentUserId())) {
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
        if (isExist && SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE)) {
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
        if (isExist || SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE)) {
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


