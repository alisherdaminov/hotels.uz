package hotels.uz.controller;

import hotels.uz.dto.Auth.ApplicationData;
import hotels.uz.dto.hotels.created.ShortAdvertsCreatedDTO;
import hotels.uz.dto.hotels.dto.ShortAdvertsDTO;
import hotels.uz.service.hotels.ShortAdvertsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/short_adverts")
public class ShortAdvertsController {

    @Autowired
    private ShortAdvertsService shortAdvertsService;

    @PreAuthorize("hasRole('HOTEL_ROLE')")
    @PostMapping("/create/{userId}")
    public ResponseEntity<ApplicationData<ShortAdvertsDTO>> createShortAdverts(@Valid @PathVariable("userId") Integer userId,
                                                                               @RequestBody ShortAdvertsCreatedDTO advertsCreatedDTO) {
        return ResponseEntity.ok().body(new ApplicationData<>(shortAdvertsService.createShortAdverts(advertsCreatedDTO, userId),
                "Success", new Date()));

    }

    @GetMapping("/fetch_all")
    public ResponseEntity<ApplicationData<List<ShortAdvertsDTO>>> findAllShortAdverts() {
        return ResponseEntity.ok().body(new ApplicationData<>(shortAdvertsService.findAllShortAdverts(),
                "Success", new Date()));
    }

    @PreAuthorize("hasRole('HOTEL_ROLE')")
    @PutMapping("/update/{advertsId}")
    public ResponseEntity<ApplicationData<ShortAdvertsDTO>> updateShortAdverts(@Valid @PathVariable("advertsId") String advertsId,
                                                                               @RequestBody ShortAdvertsCreatedDTO advertsCreatedDTO) {
        return ResponseEntity.ok().body(new ApplicationData<>(shortAdvertsService.updateShortAdverts(advertsCreatedDTO, advertsId),
                "Success", new Date()));
    }

    @PreAuthorize("hasRole('HOTEL_ROLE')")
    @DeleteMapping("/delete/{advertsId}")
    public ResponseEntity<ApplicationData<String>> deleteAdverts(@PathVariable("advertsId") String advertsId) {
        return ResponseEntity.ok().body(new ApplicationData<>(shortAdvertsService.deleteShortAdverts(advertsId),
                "Success", new Date()));
    }
}
