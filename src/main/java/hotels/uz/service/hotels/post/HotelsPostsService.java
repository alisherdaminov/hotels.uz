
package hotels.uz.service.hotels.post;

import hotels.uz.dto.hotels.created.hotel.post.HotelDetailsCreatedDTO;
import hotels.uz.dto.hotels.created.hotel.post.HotelsCreatedConditionDTO;
import hotels.uz.dto.hotels.created.hotel.post.PostCreatedDTO;
import hotels.uz.dto.hotels.created.hotel.post.QueryCreatedDTO;
import hotels.uz.dto.hotels.dto.hotel.post.HotelsConditionDTO;
import hotels.uz.dto.hotels.dto.hotel.post.HotelsDetailsDTO;
import hotels.uz.dto.hotels.dto.hotel.post.HotelsPostDTO;
import hotels.uz.entity.hotels.post.HotelsConditionEntity;
import hotels.uz.entity.hotels.post.HotelsDetailsEntity;
import hotels.uz.entity.hotels.post.HotelsEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.exceptions.AppBadException;
import hotels.uz.repository.auth.UserRepository;
import hotels.uz.repository.hotels.post.HotelDetailsRepository;
import hotels.uz.repository.hotels.post.HotelsConditionRepository;
import hotels.uz.repository.hotels.post.HotelsRepository;
import hotels.uz.repository.hotels.post.PostImageRepository;
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
    @Autowired
    private BookingService bookingService;
    @Autowired
    private PostImageRepository postImageRepository;


    //GET BY QUERY
    public List<HotelsPostDTO> findHotelsByQuery(QueryCreatedDTO queryCreatedDTO) {
        List<HotelsEntity> query = hotelsRepository.findHotelsByQuery(queryCreatedDTO.getQuery());
        return query.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    //GET BY ID of hotels post
    public HotelsEntity getHotelsPostId(String hotelsPostId) {
        return hotelsRepository.findById(hotelsPostId).orElseThrow(() -> new AppBadException("Post not found: " + hotelsPostId));
    }

    //CREATE
    public HotelsPostDTO createPost(Integer userId, PostCreatedDTO postCreatedDTO) {
        Integer currentUserId = SpringSecurityUtil.getCurrentUserId();
        if (SpringSecurityUtil.hasRole(ProfileRole.ROLE_USER) && !userId.equals(currentUserId)) {
            throw new AppBadException("You do not have any permission to create this post");
        }
        if (SpringSecurityUtil.hasRole(ProfileRole.ROLE_HOTEL) && userId.equals(currentUserId)) {
            HotelsEntity hotels = new HotelsEntity();
            hotels.setRegionName(postCreatedDTO.getRegionName());
            hotels.setProperties(postCreatedDTO.getProperties());
            hotels.setDescription(postCreatedDTO.getDescription());
            hotels.setAveragePrice(postCreatedDTO.getAveragePrice());
            hotels.setDealsStarted(postCreatedDTO.getDealsStarted());
            hotels.setUserId(currentUserId);
            hotels.setHotelsRegionImageId(postCreatedDTO.getRegionImage().getPostRegionImageCreatedId());

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

    //GET
    public List<HotelsPostDTO> findAllHotelsPost() {
        List<HotelsEntity> hotelsEntityList = hotelsRepository.findAllWithDetails();
        return hotelsEntityList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public HotelsPostDTO getHotelsPostById(String hotelsPostId) {
        HotelsEntity getHotelsPostId = getHotelsPostId(hotelsPostId);
        return mapToDTO(getHotelsPostId);
    }

    // GET BY ID
    public HotelsPostDTO getHotelsDetailsPostById(String hotelsPostId) {
        HotelsDetailsEntity entity = hotelsDetailsRepository.findById(hotelsPostId).orElseThrow(() -> new AppBadException("Post not found: " + hotelsPostId));
        return mapToDTO(entity.getHotelsEntity());
    }

    //UPDATE BY ID
    public HotelsPostDTO updateHotelsPost(String hotelsPostId, PostCreatedDTO postCreatedDTO) {
        HotelsEntity hotels = getHotelsPostId(hotelsPostId);
        Integer userId = SpringSecurityUtil.getCurrentUserId();
        if (!SpringSecurityUtil.hasRole(ProfileRole.ROLE_USER) && !hotels.getUserId().equals(userId)) {
            throw new AppBadException("You do not have permission to update this post");
        }
        String oldImage = null;
        if (!postCreatedDTO.getRegionImage().getPostRegionImageCreatedId().equals(hotels.getHotelsRegionImageId())) {
            oldImage = hotels.getHotelsRegionImageId();
        }
        hotels.setRegionName(postCreatedDTO.getRegionName());
        hotels.setProperties(postCreatedDTO.getProperties());
        hotels.setDescription(postCreatedDTO.getDescription());
        hotels.setAveragePrice(postCreatedDTO.getAveragePrice());
        hotels.setDealsStarted(postCreatedDTO.getDealsStarted());
        hotels.setHotelsRegionImageId(postCreatedDTO.getRegionImage().getPostRegionImageCreatedId());

        List<HotelsDetailsEntity> detailEntities = new ArrayList<>();
        for (HotelDetailsCreatedDTO dto : postCreatedDTO.getHotelsDetailsDTOList()) {
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
        if (SpringSecurityUtil.hasRole(ProfileRole.ROLE_HOTEL)) {
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
        postDTO.setRegionImage(postImageService.postImageDTO(entity.getHotelsRegionImageId()));
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
                    dto.setHotelPostImageId(postImageService.postImageListDTO(detail.getHotelsDetailsImageId()));
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

    public HotelsDetailsEntity toEntity(HotelDetailsCreatedDTO dto, HotelsEntity hotels) {
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

        if (dto.getPostHotelDetailsImageCreatedDTO() != null) {
            entity.setHotelsDetailsImageId(
                    dto.getPostHotelDetailsImageCreatedDTO().getPostHotelDetailsImageCreatedId()
            );
        }
        entity.setHotelsEntity(hotels); // Parent link
        hotelsDetailsRepository.save(entity);

        List<HotelsConditionEntity> conditionEntities = new ArrayList<>();
        for (HotelsCreatedConditionDTO conditionDTO : dto.getConditionNameOfItemDTOList()) {
            HotelsConditionEntity condition = new HotelsConditionEntity();
            condition.setConditionNameOfItem(conditionDTO.getConditionNameOfItem());
            condition.setHotelsDetailsEntity(entity); // Link to parent HotelsDetailsEntity
            conditionEntities.add(condition);
            hotelsConditionRepository.save(condition);
        }
        entity.setHotelsConditionEntityList(conditionEntities);
        return entity;
    }
    public HotelsPostDTO mapToDTO(HotelsEntity region) {
        HotelsPostDTO dto = new HotelsPostDTO();
        dto.setPostId(region.getHotelsId());
        dto.setRegionName(region.getRegionName());
        dto.setProperties(region.getProperties());
        dto.setDescription(region.getDescription());
        dto.setAveragePrice(region.getAveragePrice());
        dto.setDealsStarted(region.getDealsStarted());
        dto.setRegionImage(postImageService.postImageDTO(region.getHotelsRegionImageId()));

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
                hotelDTO.setDiscountAddsTitle(hotel.getDiscountAddsTitle());
                hotelDTO.setDiscountAddsDescription(hotel.getDiscountAddsDescription());
                hotelDTO.setRoomsDeluxeName(hotel.getRoomsDeluxeName());
                hotelDTO.setUserLikes(userLikesService.isLiked(hotel.getHotelsDetailsId(), SpringSecurityUtil.getCurrentUserId()));
                hotelDTO.setBookingList(bookingService.getBookingsByUserAndHotel(SpringSecurityUtil.getCurrentUserId(), hotel.getHotelsDetailsId()));
                hotelDTO.setHotelPostImageId(postImageService.postImageListDTO(hotel.getHotelsDetailsImageId()));
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

//toEntity
//    public HotelsDetailsEntity toEntity(HotelDetailsCreatedDTO dto, HotelsEntity hotels) {
//        HotelsDetailsEntity entity = new HotelsDetailsEntity();
//        entity.setHotelName(dto.getHotelName());
//        entity.setLocationShortDescription(dto.getLocationShortDescription());
//        entity.setHotelShortDescription(dto.getHotelShortDescription());
//        entity.setRoomShortDescription(dto.getRoomShortDescription());
//        entity.setPriceShortDescription(dto.getPriceShortDescription());
//        entity.setTotalPrice(dto.getTotalPrice());
//        entity.setDiscountPrice(dto.getDiscountPrice());
//        entity.setHotelsShortTitle(dto.getHotelsShortTitle());
//        entity.setCancellationTitle(dto.getCancellationTitle());
//        entity.setPaymentDescription(dto.getPaymentDescription());
//        entity.setBreakfastIncludedDescription(dto.getBreakfastIncludedDescription());
//        entity.setOrdered(dto.isOrdered());
//        entity.setDiscountAddsTitle(dto.getDiscountAddsTitle());
//        entity.setDiscountAddsDescription(dto.getDiscountAddsDescription());
//        entity.setRoomsDeluxeName(dto.getRoomsDeluxeName());
//        if (dto.getPostHotelDetailsImageCreatedDTO() != null) {
//            entity.setHotelsDetailsImageId(dto.getPostHotelDetailsImageCreatedDTO().getPostHotelDetailsImageCreatedId());
//        }
//        entity.setHotelsEntity(hotels); // Link to parent entity
//        hotelsDetailsRepository.save(entity);
//        List<HotelsCreatedConditionDTO> conditionDTOList = dto.getConditionNameOfItemDTOList();
//        List<HotelsConditionEntity> conditionEntities = new ArrayList<>();
//
//        if (conditionDTOList != null) {
//            for (HotelsCreatedConditionDTO conditionDTO : dto.getConditionNameOfItemDTOList()) {
//                HotelsConditionEntity condition = new HotelsConditionEntity();
//                condition.setConditionNameOfItem(conditionDTO.getConditionNameOfItem());
//                condition.setHotelsDetailsEntity(entity);
//                conditionEntities.add(condition);
//                hotelsConditionRepository.save(condition);
//            }
//        }
//
//        entity.setHotelsConditionEntityList(conditionEntities);
//        return entity;
//    }





