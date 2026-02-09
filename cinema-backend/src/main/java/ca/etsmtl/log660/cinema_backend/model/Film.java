package ca.etsmtl.log660.cinema_backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "FILM")
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "film_seq")
    @SequenceGenerator(name = "film_seq", sequenceName = "SEQ_FILM", allocationSize = 1)
    @Column(name = "ID_FILM")
    private Long idFilm;

    @Column(name = "TITRE", length = 200, nullable = false)
    private String titre;

    @Column(name = "ANNEE_SORTIE", precision = 4, nullable = false)
    private Integer anneeSortie;

    @Column(name = "LANGUE_ORIGINALE", length = 50, nullable = false)
    private String langueOriginale;

    @Column(name = "DUREE_MINUTES", precision = 4, nullable = false)
    private Integer dureeMinutes;

    @Column(name = "RESUME", length = 4000, nullable = false)
    private String resume;

    @Column(name = "URL_AFFICHE", length = 500)
    private String urlAffiche;

    @Column(name = "ID_REALISATEUR", nullable = false)
    private Long idRealisateur;

    // Relation 1-N avec Copie
    @OneToMany(mappedBy = "film")
    private List<Copie> copies;

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