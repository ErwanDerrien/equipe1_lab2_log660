package ca.etsmtl.log660.cinema_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.security.autoconfigure.SecurityProperties.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.etsmtl.log660.cinema_backend.dtos.ConsultationResultDTO;
import ca.etsmtl.log660.cinema_backend.dtos.LocationDTO;
import ca.etsmtl.log660.cinema_backend.dtos.RechercheDTO;
import ca.etsmtl.log660.cinema_backend.dtos.RechercheResultDTO;
import ca.etsmtl.log660.cinema_backend.services.LocationService;
import ca.etsmtl.log660.cinema_backend.services.RechercheService;
import ca.etsmtl.log660.cinema_backend.services.AuthenticationService;
import ca.etsmtl.log660.cinema_backend.util.ErrorEnum;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class MainController {
    private final RechercheService rechercheService;
    private final LocationService locationService;
    public MainController(RechercheService rechercheService, LocationService locationService, AuthenticationService userService) {
        this.rechercheService = rechercheService;
        this.locationService = locationService;
    }

    @GetMapping("/recherche")
    public ResponseEntity<List<RechercheResultDTO>> recherche(@ModelAttribute RechercheDTO rechercheDTO) {
        List<RechercheResultDTO> results = rechercheService.recherche(rechercheDTO);
        if (results.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(results);
    }

    @GetMapping("/consultation/{id}")
    public ResponseEntity<ConsultationResultDTO> consultation(@PathVariable long id) {
        ConsultationResultDTO result = rechercheService.getConsultationResult(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/location")
    public ResponseEntity<String> createLocation(Authentication authentication, @RequestBody LocationDTO locationDTO) {
        Optional<ErrorEnum> error = locationService.location(locationDTO.id(), authentication.getName());
        if (error.isEmpty()) {
            return ResponseEntity.ok("Location created successfully.");
        }
        switch (error.get()) {
            case LOCATION_ERROR_NOT_ENOUGH_COPIES:
                return ResponseEntity.status(400).body("Not enough copies available.");
            case LOCATION_ERROR_USER_ALREADY_HAS_COPY:
                return ResponseEntity.status(400).body("User already has a copy of this film.");
            case LOCATION_ERROR_INVALID_ID:
                return ResponseEntity.status(400).body("Invalid film ID.");
            default:
                return ResponseEntity.status(500).body("Unknown error.");
        }
    }
}
