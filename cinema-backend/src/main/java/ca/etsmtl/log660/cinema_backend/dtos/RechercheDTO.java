package ca.etsmtl.log660.cinema_backend.dtos;

import java.time.LocalDate;
import java.util.List;

public record RechercheDTO(
        String titre,
        String genre,
        String realisateur,
        String langue,
        String pays,
        LocalDate dateSortieDebut,
        LocalDate dateSortieFin) {
}
