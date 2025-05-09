package hotels.uz.controller;

import hotels.uz.dto.Auth.ApiResponse;
import hotels.uz.dto.hotels.created.ShortAdvertsCreatedDTO;
import hotels.uz.dto.hotels.dto.ShortAdvertsDTO;
import hotels.uz.service.hotels.ShortAdvertsService;
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

    @PreAuthorize("hasRole('HOTEL_ROLE')")
    @PostMapping("/create/{userId}")
    public ResponseEntity<ApiResponse<ShortAdvertsDTO>> createShortAdverts(@Valid @PathVariable("userId") Integer userId,
                                                                           @RequestBody ShortAdvertsCreatedDTO advertsCreatedDTO) {
        return ResponseEntity.ok().body(new ApiResponse<>(shortAdvertsService.createShortAdverts(advertsCreatedDTO, userId),
                "Success", new Date()));

    }
    @PreAuthorize("hasRole('HOTEL_ROLE')")
    @GetMapping("/fetch/{shortAdvertsId}")
    public ResponseEntity<ApiResponse<ShortAdvertsDTO>> findShortAdverts(@PathVariable("shortAdvertsId") String shortAdvertsId) {
        return ResponseEntity.ok().body(new ApiResponse<>(shortAdvertsService.findShortAdverts(shortAdvertsId),
                "Success", new Date()));
    }

    @PreAuthorize("hasRole('HOTEL_ROLE')")
    @PutMapping("/update/{advertsId}")
    public ResponseEntity<ApiResponse<ShortAdvertsDTO>> updateShortAdverts(@Valid @PathVariable("advertsId") String advertsId,
                                                                           @RequestBody ShortAdvertsCreatedDTO advertsCreatedDTO) {
        return ResponseEntity.ok().body(new ApiResponse<>(shortAdvertsService.updateShortAdverts(advertsCreatedDTO, advertsId),
                "Success", new Date()));
    }

    @PreAuthorize("hasRole('HOTEL_ROLE')")
    @DeleteMapping("/delete/{advertsId}")
    public ResponseEntity<ApiResponse<String>> deleteAdverts(@PathVariable("advertsId") String advertsId) {
        return ResponseEntity.ok().body(new ApiResponse<>(shortAdvertsService.deleteShortAdverts(advertsId),
                "Success", new Date()));
    }
}
