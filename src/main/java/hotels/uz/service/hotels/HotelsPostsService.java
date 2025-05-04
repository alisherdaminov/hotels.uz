package hotels.uz.service.hotels;

import hotels.uz.dto.hotels.created.PostCreatedDTO;
import hotels.uz.dto.hotels.dto.HotelsConditionDTO;
import hotels.uz.dto.hotels.dto.HotelsDetailsDTO;
import hotels.uz.dto.hotels.dto.PostDTO;
import hotels.uz.entity.hotels.HotelsConditionEntity;
import hotels.uz.entity.hotels.HotelsDetailsEntity;
import hotels.uz.entity.hotels.HotelsEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.exceptions.AppBadException;
import hotels.uz.repository.auth.UserRepository;
import hotels.uz.repository.hotels.HotelsRepository;
import hotels.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelsPostsService {

    @Autowired
    private HotelsRepository hotelsRepository;
    @Autowired
    private UserRepository userRepository;

    //GET BY ID of hotels post
    public HotelsEntity getHotelsPostId(String hotelsPostId) {
        return hotelsRepository.findById(hotelsPostId).orElseThrow(() -> new AppBadException("Post not found: " + hotelsPostId));
    }

    // CREATE POST
    public PostDTO createPost(Integer userId, PostCreatedDTO postCreatedDTO) {
        System.out.println("user id: createPost:---->  " + SpringSecurityUtil.getCurrentUserId());
        HotelsEntity hotels = new HotelsEntity();
        if (SpringSecurityUtil.hasRole(ProfileRole.USER_ROLE) && !userId.equals(SpringSecurityUtil.getCurrentUserId())) {
            throw new AppBadException("You do not have any permission to create this post");
        }
        if (SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE) && userId.equals(SpringSecurityUtil.getCurrentUserId())) {
            hotels.setRegionName(postCreatedDTO.getRegionName());
            hotels.setProperties(postCreatedDTO.getProperties());
            hotels.setDescription(postCreatedDTO.getDescription());
            hotels.setAveragePrice(postCreatedDTO.getAveragePrice());
            hotels.setDealsStarted(postCreatedDTO.getDealsStarted());
            hotels.setRegionImage(postCreatedDTO.getRegionImage());
            hotels.setUserId(SpringSecurityUtil.getCurrentUserId());
            List<HotelsDetailsEntity> updatedDetails = new ArrayList<>();
            if (postCreatedDTO.getHotelsDetailsDTOList() != null) {
                updatedDetails = postCreatedDTO.getHotelsDetailsDTOList().stream()
                        .map(dto -> getHotelsDetailsEntity(dto, hotels))
                        .collect(Collectors.toList());
            }
            hotels.setHotelsDetailsEntityList(updatedDetails);
            hotelsRepository.save(hotels);
        }
        return toInfoDTO(hotels);
    }

    //GET ALL POSTS
    public List<PostDTO> findAllHotelsPost() {
        List<HotelsEntity> hotelsEntityList = hotelsRepository.findAllWithDetails();
        List<PostDTO> postDTOList = new LinkedList<>();
        for (HotelsEntity entity : hotelsEntityList) {
            postDTOList.add(toPostDTO(entity));
        }
        return postDTOList;
    }

    private PostDTO toPostDTO(HotelsEntity entity) {
        PostDTO dto = new PostDTO();
        dto.setPostDtoId(entity.getHotelsId());
        dto.setRegionName(entity.getRegionName());
        dto.setProperties(entity.getProperties());
        dto.setDescription(entity.getDescription());
        dto.setAveragePrice(entity.getAveragePrice());
        dto.setDealsStarted(entity.getDealsStarted());
        dto.setRegionImage(entity.getRegionImage());
        dto.setCreatedDate(entity.getCreatedDate());

        List<HotelsDetailsDTO> detailsDTOList = entity.getHotelsDetailsEntityList().stream()
                .map(this::toHotelsDetailsDTO)
                .collect(Collectors.toList());

        dto.setHotelsDetailsDTOList(detailsDTOList);
        return dto;
    }

    private HotelsDetailsDTO toHotelsDetailsDTO(HotelsDetailsEntity entity) {
        HotelsDetailsDTO dto = new HotelsDetailsDTO();
        dto.setHotelName(entity.getHotelName());
        dto.setLocationShortDescription(entity.getLocationShortDescription());
        dto.setHotelShortDescription(entity.getHotelShortDescription());
        dto.setRoomShortDescription(entity.getRoomShortDescription());
        dto.setPriceShortDescription(entity.getPriceShortDescription());
        dto.setTotalPrice(entity.getTotalPrice());
        dto.setDiscountPrice(entity.getDiscountPrice());
        dto.setHotelsShortTitle(entity.getHotelsShortTitle());
        dto.setCancellationTitle(entity.getCancellationTitle());
        dto.setPaymentDescription(entity.getPaymentDescription());
        dto.setBreakfastIncludedDescription(entity.getBreakfastIncludedDescription());
        dto.setHotelImage(entity.getHotelImage());
        dto.setDiscountAddsTitle(entity.getDiscountAddsTitle());
        dto.setDiscountAddsDescription(entity.getDiscountAddsDescription());
        dto.setRoomsDeluxeName(entity.getRoomsDeluxeName());
        dto.setOrdered(entity.isOrdered());

        List<HotelsConditionDTO> conditionDTOList = entity.getHotelsConditionEntityList().stream()
                .map(this::toConditionDTO)
                .collect(Collectors.toList());

        dto.setConditionNameOfItemList(conditionDTOList);
        return dto;
    }

    private HotelsConditionDTO toConditionDTO(HotelsConditionEntity entity) {
        HotelsConditionDTO dto = new HotelsConditionDTO();
        dto.setConditionName(entity.getConditionNameOfItem());
        return dto;
    }

    // GET BY ID
    public PostDTO getHotelsPostById(String hotelsPostId) {
        HotelsEntity getHotelsPostId = getHotelsPostId(hotelsPostId);
        return toInfoDTO(getHotelsPostId);
    }

    //UPDATE BY ID
    public PostDTO updateHotelsPost(String hotelsPostId, PostCreatedDTO postCreatedDTO) {
        HotelsEntity hotels = getHotelsPostId(hotelsPostId);
        if (!SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE)) {
            throw new AppBadException("You do not have permission to update this post");
        }
        if (SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE)) {
            hotels.setRegionName(postCreatedDTO.getRegionName());
            hotels.setProperties(postCreatedDTO.getProperties());
            hotels.setDescription(postCreatedDTO.getDescription());
            hotels.setAveragePrice(postCreatedDTO.getAveragePrice());
            hotels.setDealsStarted(postCreatedDTO.getDealsStarted());
            hotels.setRegionImage(postCreatedDTO.getRegionImage());
            List<HotelsDetailsEntity> detailEntities = new ArrayList<>();
            for (HotelsDetailsDTO dto : postCreatedDTO.getHotelsDetailsDTOList()) {
                HotelsDetailsEntity entity = getHotelsDetailsEntity(dto, hotels);
                detailEntities.add(entity);
            }
            hotels.setHotelsDetailsEntityList(detailEntities);
            hotelsRepository.save(hotels);
        }
        return toInfoDTO(hotels);
    }

    //DELETE BY ID
    public String deleteHotelsPost(String hotelsPostId) {
        if (SpringSecurityUtil.hasRole(ProfileRole.HOTEL_ROLE)) {
            hotelsRepository.delete(getHotelsPostId(hotelsPostId));
        }
        return "Deleted";
    }

    // FOR NESTED ENTITY ON CREATE AND UPDATE METHOD
    private static HotelsDetailsEntity getHotelsDetailsEntity(HotelsDetailsDTO dto, HotelsEntity hotels) {
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
        entity.setHotelImage(dto.getHotelImage());
        entity.setOrdered(dto.isOrdered());
        entity.setDiscountAddsTitle(dto.getDiscountAddsTitle());
        entity.setDiscountAddsDescription(dto.getDiscountAddsDescription());
        entity.setRoomsDeluxeName(dto.getRoomsDeluxeName());
        entity.setHotelsEntity(hotels); // Link to parent

        // Handle nested condition list
        List<HotelsConditionEntity> conditionEntities = new ArrayList<>();
        for (HotelsConditionDTO conditionDTO : dto.getConditionNameOfItemList()) {
            HotelsConditionEntity condition = new HotelsConditionEntity();
            condition.setConditionNameOfItem(conditionDTO.getConditionName());
            condition.setHotelsDetailsEntity(entity); // Link to parent detail
            conditionEntities.add(condition);
        }
        entity.setHotelsConditionEntityList(conditionEntities);
        return entity;
    }

    // FOR NESTED ENTITY ON CREATE AND UPDATE METHOD
    public PostDTO toInfoDTO(HotelsEntity entity) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostDtoId(entity.getHotelsId());
        postDTO.setRegionName(entity.getRegionName());
        postDTO.setProperties(entity.getProperties());
        postDTO.setDescription(entity.getDescription());
        postDTO.setAveragePrice(entity.getAveragePrice());
        postDTO.setDealsStarted(entity.getDealsStarted());
        postDTO.setRegionImage(entity.getRegionImage());
        postDTO.setCreatedDate(entity.getCreatedDate());

        // Map HotelsDetails list
        List<HotelsDetailsDTO> hotelsDetailsDTOList = new ArrayList<>();
        for (HotelsDetailsEntity detail : entity.getHotelsDetailsEntityList()) {
            HotelsDetailsDTO dto = new HotelsDetailsDTO();
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
            dto.setHotelImage(detail.getHotelImage());
            dto.setOrdered(detail.isOrdered());
            dto.setDiscountAddsTitle(detail.getDiscountAddsTitle());
            dto.setDiscountAddsDescription(detail.getDiscountAddsDescription());
            dto.setRoomsDeluxeName(detail.getRoomsDeluxeName());

            // Map condition list
            List<HotelsConditionDTO> conditionDTOList = new ArrayList<>();
            for (HotelsConditionEntity condition : detail.getHotelsConditionEntityList()) {
                HotelsConditionDTO conditionDTO = new HotelsConditionDTO();
                conditionDTO.setConditionName(condition.getConditionNameOfItem());
                conditionDTOList.add(conditionDTO);
            }

            dto.setConditionNameOfItemList(conditionDTOList);
            hotelsDetailsDTOList.add(dto);
        }
        postDTO.setHotelsDetailsDTOList(hotelsDetailsDTOList);
        return postDTO;
    }


}
