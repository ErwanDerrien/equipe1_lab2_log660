package ca.etsmtl.log660.cinema_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.etsmtl.log660.cinema_backend.dtos.AuthResponseDTO;
import ca.etsmtl.log660.cinema_backend.dtos.LoginDTO;
import ca.etsmtl.log660.cinema_backend.dtos.RegisterDTO;
import ca.etsmtl.log660.cinema_backend.services.JWTService;
import ca.etsmtl.log660.cinema_backend.services.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final AuthenticationService userService;

    public AuthController(AuthenticationManager authenticationManager, JWTService jwtService,
            AuthenticationService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.courriel(),
                            request.password()));
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(new AuthResponseDTO(jwtService.generateToken(request.courriel())));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO request) {
        try {
            userService.register(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.courriel(),
                            request.motDePasse()));
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Inscription réussie, mais échec de l'authentification.");
        }

        return ResponseEntity.ok(new AuthResponseDTO(jwtService.generateToken(request.courriel())));
    }
}