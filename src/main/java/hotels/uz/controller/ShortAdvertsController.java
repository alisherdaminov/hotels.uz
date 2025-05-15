package hotels.uz.controller;

import hotels.uz.dto.Auth.ApiResponse;
import hotels.uz.dto.hotels.created.hotel.adverts.ShortAdvertsCreatedDTO;
import hotels.uz.dto.hotels.dto.hotel.adverts.ShortAdvertsDTO;
import hotels.uz.service.hotels.adverts.ShortAdvertsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/short_adverts")
public class ShortAdvertsController {

    @Autowired
    private ShortAdvertsService shortAdvertsService;

    @PreAuthorize("hasRole('ROLE_HOTEL')")
    @PostMapping("/create/{userId}")
    public ResponseEntity<ApiResponse<ShortAdvertsDTO>> createShortAdverts(@Valid @PathVariable("userId") Integer userId,
                                                                           @RequestBody ShortAdvertsCreatedDTO advertsCreatedDTO) {
        return ResponseEntity.ok().body(new ApiResponse<>(shortAdvertsService.createShortAdverts(advertsCreatedDTO, userId),
                "Success", new Date()));

    }
    @PreAuthorize("hasRole('ROLE_HOTEL')")
    @GetMapping("/fetch/{shortAdvertsId}")
    public ResponseEntity<ApiResponse<ShortAdvertsDTO>> findShortAdverts(@PathVariable("shortAdvertsId") String shortAdvertsId) {
        return ResponseEntity.ok().body(new ApiResponse<>(shortAdvertsService.findShortAdverts(shortAdvertsId),
                "Success", new Date()));
    }

    @PreAuthorize("hasRole('ROLE_HOTEL')")
    @PutMapping("/update/{advertsId}")
    public ResponseEntity<ApiResponse<ShortAdvertsDTO>> updateShortAdverts(@Valid @PathVariable("advertsId") String advertsId,
                                                                           @RequestBody ShortAdvertsCreatedDTO advertsCreatedDTO) {
        return ResponseEntity.ok().body(new ApiResponse<>(shortAdvertsService.updateShortAdverts(advertsCreatedDTO, advertsId),
                "Success", new Date()));
    }

    @PreAuthorize("hasRole('ROLE_HOTEL')")
    @DeleteMapping("/delete/{advertsId}")
    public ResponseEntity<ApiResponse<String>> deleteAdverts(@PathVariable("advertsId") String advertsId) {
        return ResponseEntity.ok().body(new ApiResponse<>(shortAdvertsService.deleteShortAdverts(advertsId),
                "Success", new Date()));
    }
}
