package ca.etsmtl.log660.cinema_backend.dtos;

import java.time.LocalDate;

public record RegisterDTO (
    String nom,
    String prenom,
    String courriel,
    String telephone,
    String adresseCivique,
    String rue,
    String ville,
    String province,
    String codePostal,
    LocalDate dateNaissance,
    String motDePasse) {}