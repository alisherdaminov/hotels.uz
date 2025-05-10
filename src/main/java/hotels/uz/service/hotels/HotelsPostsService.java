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
import java.util.Collections;
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
        return hotelsEntityList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    private PostDTO mapToDTO(HotelsEntity region) {
        PostDTO dto = new PostDTO();
        dto.setRegionName(region.getRegionName());
        dto.setProperties(region.getProperties());
        dto.setDescription(region.getDescription());
        dto.setAveragePrice(region.getAveragePrice());
        dto.setDealsStarted(region.getDealsStarted());
        dto.setRegionImage(region.getRegionImage());
        System.out.println("region: " + region.getRegionName());
        for (HotelsDetailsEntity entity : region.getHotelsDetailsEntityList()) {
            System.out.println("entity: " + entity.getHotelName());
        }
        dto.setHotelsDetailsDTOList(
                region.getHotelsDetailsEntityList().stream().map(hotel -> {
                    System.out.println("hotel: " + hotel.getHotelName());

                    HotelsDetailsDTO hotelDTO = new HotelsDetailsDTO();
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
                    hotelDTO.setHotelImage(hotel.getHotelImage());
                    hotelDTO.setDiscountAddsTitle(hotel.getDiscountAddsTitle());
                    hotelDTO.setDiscountAddsDescription(hotel.getDiscountAddsDescription());
                    hotelDTO.setRoomsDeluxeName(hotel.getRoomsDeluxeName());
                    hotelDTO.setOrdered(hotel.isOrdered());

                    hotelDTO.setConditionNameOfItemList(
                            hotel.getHotelsConditionEntityList().stream().map(cond -> {
                                HotelsConditionDTO condDTO = new HotelsConditionDTO();
                                condDTO.setConditionName(cond.getConditionNameOfItem());
                                return condDTO;
                            }).collect(Collectors.toList())

                    );
                    System.out.println("Condition size: " + hotel.getHotelsConditionEntityList().size());

                    System.out.println("hotelDTO: " + hotelDTO.getHotelName());
                    return hotelDTO;
                }).collect(Collectors.toList())
        );

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
    public PostDTO toInfoDTO(HotelsEntity entity) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(entity.getHotelsId());
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


}
