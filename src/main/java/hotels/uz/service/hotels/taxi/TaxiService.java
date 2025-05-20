package hotels.uz.service.hotels.taxi;

import hotels.uz.dto.hotels.created.hotel.taxi.TaxiCategoryCreatedDTO;
import hotels.uz.dto.hotels.created.hotel.taxi.TaxiCreatedDTO;
import hotels.uz.dto.hotels.dto.hotel.taxi.TaxiCategoryDTO;
import hotels.uz.dto.hotels.dto.hotel.taxi.TaxiDTO;
import hotels.uz.entity.hotels.taxi.TaxiCategoryEntity;
import hotels.uz.entity.hotels.taxi.TaxiEntity;
import hotels.uz.enums.ProfileRole;
import hotels.uz.exceptions.AppBadException;
import hotels.uz.repository.hotels.taxi.TaxiCategoryRepository;
import hotels.uz.repository.hotels.taxi.TaxiRepository;
import hotels.uz.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxiService {


    @Autowired
    private TaxiRepository taxiRepository;
    @Autowired
    private TaxiCategoryRepository taxiCategoryRepository;

    public TaxiDTO createTaxi(TaxiCreatedDTO createdDTO) {
        TaxiEntity entity = new TaxiEntity();
        Integer userId = SpringSecurityUtil.getCurrentUserId();
        entity.setTaxiTitle(createdDTO.getTaxiTitle());
        entity.setTaxiPhoneNumber(createdDTO.getTaxiPhoneNumber());
        entity.setUserId(userId);
        // entity.setTaxiLogo();
        TaxiEntity savedTaxiData = taxiRepository.save(entity);
        List<TaxiCategoryEntity> taxiEntityList = new ArrayList<>();
        if (createdDTO.getCategoryCreatedList() != null) {
            taxiEntityList = createdDTO.getCategoryCreatedList().stream().map(dto -> toEntity(dto, savedTaxiData)).toList();
        }
        entity.setTaxiCategoryList(taxiEntityList);
        return toDTO(entity);
    }

    public List<TaxiDTO> getAllTaxiData() {
        List<TaxiEntity> taxiEntityList = taxiRepository.findAll();
        return taxiEntityList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public TaxiDTO updateTaxi(String taxiId, TaxiCreatedDTO createdDTO) {
        TaxiEntity taxiEntity = getTaxiId(taxiId);
        if (SpringSecurityUtil.hasRole(ProfileRole.ROLE_ADMIN)) {
            taxiEntity.setTaxiTitle(createdDTO.getTaxiTitle());
            taxiEntity.setTaxiPhoneNumber(createdDTO.getTaxiPhoneNumber());
            // taxiEntity.setTaxiLogo();
            List<TaxiCategoryEntity> taxiCategoryEntityList = new ArrayList<>();
            for (TaxiCategoryCreatedDTO categoryCreatedDTO : createdDTO.getCategoryCreatedList()) {
                TaxiCategoryEntity entity = toEntity(categoryCreatedDTO, taxiEntity);
                taxiCategoryEntityList.add(entity);
            }
            taxiEntity.setTaxiCategoryList(taxiCategoryEntityList);
        }
        return toDTO(taxiEntity);
    }

    public String deleteTaxi(String taxiId) {
        if (SpringSecurityUtil.hasRole(ProfileRole.ROLE_ADMIN)) {
            taxiRepository.delete(getTaxiId(taxiId));
        }
        return "Deleted: " + taxiId;
    }


    private TaxiCategoryEntity toEntity(TaxiCategoryCreatedDTO dto, TaxiEntity savedTaxiData) {
        TaxiCategoryEntity entity = new TaxiCategoryEntity();
        entity.setTaxiCategoryName(dto.getTaxiCategoryName());
        entity.setTaxiEntity(savedTaxiData); // Parent link
        taxiCategoryRepository.save(entity);
        return entity;
    }


    public TaxiDTO toDTO(TaxiEntity entity) {
        TaxiDTO dto = new TaxiDTO();
        dto.setTaxiId(entity.getTaxiId());
        dto.setTaxiTitle(entity.getTaxiTitle());
        dto.setTaxiPhoneNumber(entity.getTaxiPhoneNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        //dto.setTaxiLogo();
        List<TaxiCategoryDTO> taxiCategoryDTOList = entity.getTaxiCategoryList().stream().map(
                taxiCategoryEntity -> {
                    TaxiCategoryDTO categoryDTO = new TaxiCategoryDTO();
                    categoryDTO.setTaxiCategoryId(taxiCategoryEntity.getTaxiId());
                    categoryDTO.setTaxiCategoryName(taxiCategoryEntity.getTaxiCategoryName());
                    return categoryDTO;
                }).collect(Collectors.toList());
        dto.setTaxiCategoryList(taxiCategoryDTOList);
        return dto;
    }

    public TaxiDTO mapToDTO(TaxiEntity entity) {
        TaxiDTO dto = new TaxiDTO();
        dto.setTaxiId(entity.getTaxiId());
        dto.setTaxiTitle(entity.getTaxiTitle());
        dto.setTaxiPhoneNumber(entity.getTaxiPhoneNumber());
        dto.setCreatedDate(entity.getCreatedDate());
        //dto.setTaxiLogo();
        List<TaxiCategoryDTO> taxiCategoryDTOList = new ArrayList<>();
        if (entity.getTaxiCategoryList() != null) {
            for (TaxiCategoryEntity taxi : entity.getTaxiCategoryList()) {
                TaxiCategoryDTO categoryDTO = new TaxiCategoryDTO();
                categoryDTO.setTaxiCategoryId(taxi.getTaxiId());
                categoryDTO.setTaxiCategoryName(taxi.getTaxiCategoryName());
                taxiCategoryDTOList.add(categoryDTO);
            }

            dto.setTaxiCategoryList(taxiCategoryDTOList);
        }
        return dto;
    }

    public TaxiEntity getTaxiId(String taxiId) {
        return taxiRepository.findById(taxiId).orElseThrow(() -> new AppBadException("Taxi id not found: " + taxiId));
    }
}
