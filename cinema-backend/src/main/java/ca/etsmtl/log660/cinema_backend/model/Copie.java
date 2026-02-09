package ca.etsmtl.log660.cinema_backend.model;

public class Copie {

    private Long idCopie;

    private String numeroCode;

    private String disponible;

    // Relation N-1 avec Film
    private Film film;

    // Constructeur vide
    public Copie() {
    }

    // Getters et Setters
    public Long getIdCopie() {
        return idCopie;
    }

    public void setIdCopie(Long idCopie) {
        this.idCopie = idCopie;
    }

    public String getNumeroCode() {
        return numeroCode;
    }

    public void setNumeroCode(String numeroCode) {
        this.numeroCode = numeroCode;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }
}