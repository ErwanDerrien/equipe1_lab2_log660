package ca.etsmtl.log660.cinema_backend.dtos;

import java.util.List;

public record ConsultationResultDTO(
    long id,
    String titre,
    String urlAffiche,
    long idRealisateur,
    String nomRealisateur,
    Integer anneeSortie,
    String langueOriginale,
    Integer dureeMinutes,
    String resume,
    int nbCopies,
    List<ConsultationActeurDTO> acteurs) {}