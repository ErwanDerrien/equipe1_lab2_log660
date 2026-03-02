package ca.etsmtl.log660.cinema_backend.dtos;

public record RechercheResultDTO(
        long id,
        String titre,
        String urlAffiche,
        String nomRealisateur) {
}
