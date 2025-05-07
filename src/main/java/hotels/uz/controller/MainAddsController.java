package hotels.uz.controller;

import hotels.uz.dto.Auth.ApplicationData;
import hotels.uz.dto.hotels.created.MainAddsCreatedDTO;
import hotels.uz.dto.hotels.dto.MainAddsDTO;
import hotels.uz.service.hotels.MainAddsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/main_adds")
public class MainAddsController {

    @Autowired
    private MainAddsService mainAddsService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<ApplicationData<MainAddsDTO>> createAdds(@Valid @RequestBody MainAddsCreatedDTO dto,
                                                                   @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok().body(new ApplicationData<>(mainAddsService.createAdds(dto, userId),
                "Success", new Date()));
    }

    @GetMapping("/fetch/{mainAddsId}")
    public ResponseEntity<ApplicationData<MainAddsDTO>> findAddsById(@PathVariable("mainAddsId") String mainAddsId) {
        return ResponseEntity.ok().body(new ApplicationData<>(mainAddsService.findAddsById(mainAddsId),
                "Success", new Date()));
    }

    @DeleteMapping("/delete/{mainAddsId}")
    public ResponseEntity<ApplicationData<String>> deleteAdds(@PathVariable("mainAddsId") String mainAddsId) {
        return ResponseEntity.ok().body(new ApplicationData<>(mainAddsService.deleteAdds(mainAddsId),
                "Success", new Date()));
    }
}
