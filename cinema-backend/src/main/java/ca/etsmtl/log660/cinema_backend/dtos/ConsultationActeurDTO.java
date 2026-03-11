package ca.etsmtl.log660.cinema_backend.dtos;

public record ConsultationActeurDTO(
        long idActeur,
        String prenom,
        String nom,
        String nomPersonnage) {
}