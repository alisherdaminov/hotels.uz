package hotels.uz.controller;

import hotels.uz.dto.Auth.ApiResponse;
import hotels.uz.dto.hotels.created.hotel.taxi.TaxiCreatedDTO;
import hotels.uz.dto.hotels.dto.hotel.taxi.TaxiDTO;
import hotels.uz.service.hotels.taxi.TaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/taxi")
public class TaxiController {

    @Autowired
    private TaxiService taxiService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<TaxiDTO>> createTaxi(@RequestBody TaxiCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new ApiResponse<>(taxiService.createTaxi(createdDTO),
                "Success", new Date()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/fetch_all")
    public ResponseEntity<ApiResponse<List<TaxiDTO>>> getAllTaxiData() {
        return ResponseEntity.ok().body(new ApiResponse<>(taxiService.getAllTaxiData(),
                "Success", new Date()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update/{taxiId}")
    public ResponseEntity<ApiResponse<TaxiDTO>> updateTaxi(@PathVariable("taxiId") String taxiId,
                                                           @RequestBody TaxiCreatedDTO createdDTO) {
        return ResponseEntity.ok().body(new ApiResponse<>(taxiService.updateTaxi(taxiId, createdDTO),
                "Success", new Date()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{taxiId}")
    public ResponseEntity<ApiResponse<String>> createTaxi(@PathVariable("taxiId") String taxiId) {
        return ResponseEntity.ok().body(new ApiResponse<>(taxiService.deleteTaxi(taxiId),
                "Success", new Date()));
    }

}
