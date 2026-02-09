package ca.etsmtl.log660.cinema_backend.model;

import java.util.List;

public class Film {

    private Long idFilm;

    private String titre;

    private Integer anneeSortie;

    private String langueOriginale;

    private Integer dureeMinutes;

    private String resume;

    private String urlAffiche;

    private Long idRealisateur;

    // Relation 1-N avec Copie
    private List<Copie> copies;

    // Constructeur vide
    public Film() {
    }

    // Getters et Setters
    public Long getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(Long idFilm) {
        this.idFilm = idFilm;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Integer getAnneeSortie() {
        return anneeSortie;
    }

    public void setAnneeSortie(Integer anneeSortie) {
        this.anneeSortie = anneeSortie;
    }

    public String getLangueOriginale() {
        return langueOriginale;
    }

    public void setLangueOriginale(String langueOriginale) {
        this.langueOriginale = langueOriginale;
    }

    public Integer getDureeMinutes() {
        return dureeMinutes;
    }

    public void setDureeMinutes(Integer dureeMinutes) {
        this.dureeMinutes = dureeMinutes;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getUrlAffiche() {
        return urlAffiche;
    }

    public void setUrlAffiche(String urlAffiche) {
        this.urlAffiche = urlAffiche;
    }

    public Long getIdRealisateur() {
        return idRealisateur;
    }

    public void setIdRealisateur(Long idRealisateur) {
        this.idRealisateur = idRealisateur;
    }

    public List<Copie> getCopies() {
        return copies;
    }

    public void setCopies(List<Copie> copies) {
        this.copies = copies;
    }
}