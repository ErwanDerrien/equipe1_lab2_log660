package ca.etsmtl.log660.cinema_backend.services;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ca.etsmtl.log660.cinema_backend.dtos.RegisterDTO;
import ca.etsmtl.log660.cinema_backend.facade.AuthFacade;
import ca.etsmtl.log660.cinema_backend.model.Utilisateur;

import java.sql.Date;
import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {

    private final AuthFacade AuthFacade;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthFacade AuthFacade,
                                 PasswordEncoder passwordEncoder) {
        this.AuthFacade = AuthFacade;
        this.passwordEncoder = passwordEncoder;
    }

    public Utilisateur findByCourriel(String courriel) {
        return AuthFacade.findByCourriel(courriel);
    }

    public boolean register(RegisterDTO dto) {

        if (AuthFacade.findByCourriel(dto.courriel()) != null) {
            throw new RuntimeException("Email already registered");
        }

        if (dto.motDePasse() == null || !dto.motDePasse().matches("^[A-Za-z0-9]{5,}$")) {
            throw new RuntimeException(
                    "Le mot de passe doit contenir au moins 5 caractères et seulement des lettres et des chiffres.");
        }

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setNom(dto.nom());
        utilisateur.setPrenom(dto.prenom());
        utilisateur.setCourriel(dto.courriel());
        utilisateur.setTelephone(dto.telephone());
        utilisateur.setAdresseCivique(dto.adresseCivique());
        utilisateur.setRue(dto.rue());
        utilisateur.setVille(dto.ville());
        utilisateur.setProvince(dto.province());
        utilisateur.setCodePostal(dto.codePostal());
        utilisateur.setDateNaissance(Date.valueOf(dto.dateNaissance()));
        utilisateur.setMotDePasse(passwordEncoder.encode(dto.motDePasse()));

        AuthFacade.saveUtilisateur(utilisateur);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Utilisateur utilisateur = AuthFacade.findByCourriel(username);

        if (utilisateur == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new User(
                utilisateur.getCourriel(),
                utilisateur.getMotDePasse(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}