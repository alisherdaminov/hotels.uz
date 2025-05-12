package hotels.uz.service.hotels.post;

import hotels.uz.dto.hotels.created.PostCreatedDTO;
import hotels.uz.dto.hotels.dto.HotelsConditionDTO;
import hotels.uz.dto.hotels.dto.HotelsDetailsDTO;
import hotels.uz.dto.hotels.dto.HotelsPostDTO;
import hotels.uz.entity.hotels.HotelsConditionEntity;
import hotels.uz.entity.hotels.HotelsDetailsEntity;
import hotels.uz.entity.hotels.HotelsEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.exceptions.AppBadException;
import hotels.uz.repository.auth.UserRepository;
import hotels.uz.repository.hotels.HotelDetailsRepository;
import hotels.uz.repository.hotels.HotelsConditionRepository;
import hotels.uz.repository.hotels.HotelsRepository;
import hotels.uz.service.hotels.likes.UserLikesService;
import hotels.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelsPostsService {

    @Autowired
    private HotelsRepository hotelsRepository;
    @Autowired
    private HotelDetailsRepository hotelsDetailsRepository;
    @Autowired
    private HotelsConditionRepository hotelsConditionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostImageService postImageService;
    @Autowired
    private UserLikesService userLikesService;

    //GET BY ID of hotels post
    public HotelsEntity getHotelsPostId(String hotelsPostId) {
        return hotelsRepository.findById(hotelsPostId).orElseThrow(() -> new AppBadException("Post not found: " + hotelsPostId));
    }


    public HotelsPostDTO createPost(Integer userId, PostCreatedDTO postCreatedDTO) {
        Integer currentUserId = SpringSecurityUtil.getCurrentUserId();
        if (SpringSecurityUtil.hasRole(ProfileRole.USER_ROLE) && !userId.equals(currentUserId)) {
            throw new AppBadException("You do not have any permission to create this post");
        }
        if (SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE) && userId.equals(currentUserId)) {
            HotelsEntity hotels = new HotelsEntity();
            hotels.setRegionName(postCreatedDTO.getRegionName());
            hotels.setProperties(postCreatedDTO.getProperties());
            hotels.setDescription(postCreatedDTO.getDescription());
            hotels.setAveragePrice(postCreatedDTO.getAveragePrice());
            hotels.setDealsStarted(postCreatedDTO.getDealsStarted());
            hotels.setUserId(currentUserId);
//            if (postCreatedDTO.getRegionImage() != null) {
//                hotels.setPostImageId(postCreatedDTO.getRegionImage().getPostImageCreatedId());
//            }
            HotelsEntity savedHotel = hotelsRepository.save(hotels);

            List<HotelsDetailsEntity> detailsList = new ArrayList<>();
            if (postCreatedDTO.getHotelsDetailsDTOList() != null) {
                detailsList = postCreatedDTO.getHotelsDetailsDTOList().stream()
                        .map(dto -> toEntity(dto, savedHotel))
                        .collect(Collectors.toList());
            }
            hotels.setHotelsDetailsEntityList(detailsList);
            return toDTO(savedHotel);
        }

        throw new AppBadException("Unauthorized attempt to create post");
    }

    public List<HotelsPostDTO> findAllHotelsPost() {
        List<HotelsEntity> hotelsEntityList = hotelsRepository.findAllWithDetails();
        return hotelsEntityList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public HotelsPostDTO getHotelsPostById(String hotelsPostId) {
        HotelsEntity getHotelsPostId = getHotelsPostId(hotelsPostId);
        return toDTO(getHotelsPostId);
    }

    //UPDATE BY ID
    public HotelsPostDTO updateHotelsPost(String hotelsPostId, PostCreatedDTO postCreatedDTO) {
        HotelsEntity hotels = getHotelsPostId(hotelsPostId);
        Integer userId = SpringSecurityUtil.getCurrentUserId();
        if (!SpringSecurityUtil.hasRole(ProfileRole.USER_ROLE) && !hotels.getUserId().equals(userId)) {
            throw new AppBadException("You do not have permission to update this post");
        }
        String oldImage = null;
        if (!postCreatedDTO.getRegionImage().getPostImageCreatedId().equals(hotels.getPostImageId())) {
            oldImage = hotels.getPostImageId();
        }
        hotels.setRegionName(postCreatedDTO.getRegionName());
        hotels.setProperties(postCreatedDTO.getProperties());
        hotels.setDescription(postCreatedDTO.getDescription());
        hotels.setAveragePrice(postCreatedDTO.getAveragePrice());
        hotels.setDealsStarted(postCreatedDTO.getDealsStarted());
        hotels.setPostImageId(postCreatedDTO.getRegionImage().getPostImageCreatedId());

        List<HotelsDetailsEntity> detailEntities = new ArrayList<>();
        for (HotelsDetailsDTO dto : postCreatedDTO.getHotelsDetailsDTOList()) {
            HotelsDetailsEntity entity = toEntity(dto, hotels);
            detailEntities.add(entity);
        }
        hotels.setHotelsDetailsEntityList(detailEntities);
        hotelsRepository.save(hotels);
        if (oldImage != null) {
            postImageService.deletePostById(oldImage);
        }
        return toDTO(hotels);
    }

    //DELETE BY ID
    public String deleteHotelsPost(String hotelsPostId) {
        if (SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE)) {
            hotelsRepository.delete(getHotelsPostId(hotelsPostId));
        }
        return "Deleted";
    }

    //toDTO
    public HotelsPostDTO toDTO(HotelsEntity entity) {
        HotelsPostDTO postDTO = new HotelsPostDTO();
        postDTO.setPostId(entity.getHotelsId());
        postDTO.setRegionName(entity.getRegionName());
        postDTO.setProperties(entity.getProperties());
        postDTO.setDescription(entity.getDescription());
        postDTO.setAveragePrice(entity.getAveragePrice());
        postDTO.setDealsStarted(entity.getDealsStarted());
        // postDTO.setRegionImage(postImageService.postImageDTO(entity.getPostImageId()));
        postDTO.setCreatedDate(entity.getCreatedDate());

        List<HotelsDetailsDTO> hotelsDetailsDTOList = entity.getHotelsDetailsEntityList().stream()
                .map(detail -> {
                    HotelsDetailsDTO dto = new HotelsDetailsDTO();
                    dto.setHotelsDetailsId(detail.getHotelsDetailsId());
                    dto.setHotelName(detail.getHotelName());
                    dto.setLocationShortDescription(detail.getLocationShortDescription());
                    dto.setHotelShortDescription(detail.getHotelShortDescription());
                    dto.setRoomShortDescription(detail.getRoomShortDescription());
                    dto.setPriceShortDescription(detail.getPriceShortDescription());
                    dto.setTotalPrice(detail.getTotalPrice());
                    dto.setDiscountPrice(detail.getDiscountPrice());
                    dto.setHotelsShortTitle(detail.getHotelsShortTitle());
                    dto.setCancellationTitle(detail.getCancellationTitle());
                    dto.setPaymentDescription(detail.getPaymentDescription());
                    dto.setBreakfastIncludedDescription(detail.getBreakfastIncludedDescription());
                    // dto.setHotelImage(postImageService.postImageDTO(detail.getPostImagesDetailsId()));
                    dto.setOrdered(detail.isOrdered());
                    dto.setDiscountAddsTitle(detail.getDiscountAddsTitle());
                    dto.setDiscountAddsDescription(detail.getDiscountAddsDescription());
                    dto.setRoomsDeluxeName(detail.getRoomsDeluxeName());

                    List<HotelsConditionDTO> conditionDTOList = detail.getHotelsConditionEntityList().stream().map(condition -> {
                        HotelsConditionDTO condDTO = new HotelsConditionDTO();
                        condDTO.setConditionId(condition.getHotelsConditionId());
                        condDTO.setConditionName(condition.getConditionNameOfItem());
                        return condDTO;
                    }).collect(Collectors.toList());

                    dto.setConditionNameOfItemList(conditionDTOList);
                    return dto;
                }).collect(Collectors.toList());

        postDTO.setHotelsDetailsList(hotelsDetailsDTOList);
        return postDTO;
    }

    //toEntity
    public HotelsDetailsEntity toEntity(HotelsDetailsDTO dto, HotelsEntity hotels) {
        HotelsDetailsEntity entity = new HotelsDetailsEntity();
        entity.setHotelName(dto.getHotelName());
        entity.setLocationShortDescription(dto.getLocationShortDescription());
        entity.setHotelShortDescription(dto.getHotelShortDescription());
        entity.setRoomShortDescription(dto.getRoomShortDescription());
        entity.setPriceShortDescription(dto.getPriceShortDescription());
        entity.setTotalPrice(dto.getTotalPrice());
        entity.setDiscountPrice(dto.getDiscountPrice());
        entity.setHotelsShortTitle(dto.getHotelsShortTitle());
        entity.setCancellationTitle(dto.getCancellationTitle());
        entity.setPaymentDescription(dto.getPaymentDescription());
        entity.setBreakfastIncludedDescription(dto.getBreakfastIncludedDescription());
        entity.setOrdered(dto.isOrdered());
        entity.setDiscountAddsTitle(dto.getDiscountAddsTitle());
        entity.setDiscountAddsDescription(dto.getDiscountAddsDescription());
        entity.setRoomsDeluxeName(dto.getRoomsDeluxeName());
        entity.setHotelsEntity(hotels); // Link to parent entity
        hotelsDetailsRepository.save(entity);
        List<HotelsConditionEntity> conditionEntities = new ArrayList<>();
        for (HotelsConditionDTO conditionDTO : dto.getConditionNameOfItemList()) {
            HotelsConditionEntity condition = new HotelsConditionEntity();
            condition.setConditionNameOfItem(conditionDTO.getConditionName());
            condition.setHotelsDetailsEntity(entity); // Link to parent HotelsDetailsEntity
            conditionEntities.add(condition);
            hotelsConditionRepository.save(condition);
        }
        entity.setHotelsConditionEntityList(conditionEntities);
        return entity;
    }

    //mapToDTO
    public HotelsPostDTO mapToDTO(HotelsEntity region) {
        HotelsPostDTO dto = new HotelsPostDTO();
        dto.setPostId(region.getHotelsId());
        dto.setRegionName(region.getRegionName());
        dto.setProperties(region.getProperties());
        dto.setDescription(region.getDescription());
        dto.setAveragePrice(region.getAveragePrice());
        dto.setDealsStarted(region.getDealsStarted());
        List<HotelsDetailsDTO> hotelsDetailsDTOList = new ArrayList<>();
        if (region.getHotelsDetailsEntityList() != null) {
            for (HotelsDetailsEntity hotel : region.getHotelsDetailsEntityList()) {
                HotelsDetailsDTO hotelDTO = new HotelsDetailsDTO();
                hotelDTO.setHotelsDetailsId(hotel.getHotelsDetailsId());
                hotelDTO.setHotelName(hotel.getHotelName());
                hotelDTO.setLocationShortDescription(hotel.getLocationShortDescription());
                hotelDTO.setHotelShortDescription(hotel.getHotelShortDescription());
                hotelDTO.setRoomShortDescription(hotel.getRoomShortDescription());
                hotelDTO.setPriceShortDescription(hotel.getPriceShortDescription());
                hotelDTO.setTotalPrice(hotel.getTotalPrice());
                hotelDTO.setDiscountPrice(hotel.getDiscountPrice());
                hotelDTO.setHotelsShortTitle(hotel.getHotelsShortTitle());
                hotelDTO.setCancellationTitle(hotel.getCancellationTitle());
                hotelDTO.setPaymentDescription(hotel.getPaymentDescription());
                hotelDTO.setBreakfastIncludedDescription(hotel.getBreakfastIncludedDescription());

                if (hotel.getPostImagesDetailsId() != null) {
                    hotelDTO.setHotelImage(postImageService.postImageDTO(hotel.getPostImagesDetailsId()));
                }
                hotelDTO.setDiscountAddsTitle(hotel.getDiscountAddsTitle());
                hotelDTO.setDiscountAddsDescription(hotel.getDiscountAddsDescription());
                hotelDTO.setRoomsDeluxeName(hotel.getRoomsDeluxeName());
                hotelDTO.setUserLikes(userLikesService.isLiked(hotel.getHotelsDetailsId(), SpringSecurityUtil.getCurrentUserId()));
                hotelDTO.setOrdered(hotel.isOrdered());

                List<HotelsConditionDTO> conditionDTOList = new ArrayList<>();
                if (hotel.getHotelsConditionEntityList() != null) {
                    hotel.getHotelsConditionEntityList().forEach(cond -> {
                        HotelsConditionDTO condDTO = new HotelsConditionDTO();
                        condDTO.setConditionId(cond.getHotelsConditionId());
                        condDTO.setConditionName(cond.getConditionNameOfItem());
                        conditionDTOList.add(condDTO);
                    });
                }
                hotelDTO.setConditionNameOfItemList(conditionDTOList);
                hotelsDetailsDTOList.add(hotelDTO);
            }
        }
        dto.setHotelsDetailsList(hotelsDetailsDTOList);
        return dto;
    }

}
