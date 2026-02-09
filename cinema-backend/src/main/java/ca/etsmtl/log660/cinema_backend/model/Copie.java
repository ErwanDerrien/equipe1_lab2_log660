package ca.etsmtl.log660.cinema_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "COPIE")
public class Copie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "copie_seq")
    @SequenceGenerator(name = "copie_seq", sequenceName = "SEQ_COPIE", allocationSize = 1)
    @Column(name = "ID_COPIE")
    private Long idCopie;

    @Column(name = "NUMERO_CODE", length = 50, nullable = false, unique = true)
    private String numeroCode;

    @Column(name = "DISPONIBLE", length = 1, nullable = false)
    private String disponible;

    // Relation N-1 avec Film
    @ManyToOne
    @JoinColumn(name = "ID_FILM", nullable = false)
    private Film film;

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