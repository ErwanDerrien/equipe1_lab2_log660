package ca.etsmtl.log660.cinema_backend.dtos;

public record ConsultationResultDTO(
    long id,
    String titre,
    String urlAffiche,
    String nomRealisateur,
    Integer anneeSortie,
    String langueOriginale,
    Integer dureeMinutes,
    String resume,
    int nbCopies) {}
