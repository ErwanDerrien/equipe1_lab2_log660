package ca.etsmtl.log660.cinema_backend.dtos;

import java.time.LocalDate;

public record RechercheDTO(
        String titre,
        String genre,
        String realisateur,
        String acteur,
        String langue,
        String pays,
        LocalDate dateSortieDebut,
        LocalDate dateSortieFin) {
}