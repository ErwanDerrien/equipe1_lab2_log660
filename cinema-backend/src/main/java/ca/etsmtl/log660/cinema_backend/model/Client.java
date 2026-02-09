package ca.etsmtl.log660.cinema_backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "CLIENT")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    @SequenceGenerator(name = "client_seq", sequenceName = "SEQ_CLIENT", allocationSize = 1)
    @Column(name = "ID_CLIENT")
    private Long idClient;

    @Column(name = "TYPE_FORFAIT", length = 1, nullable = false)
    private String typeForfait;

    // Relation 1-1 avec Utilisateur
    @OneToOne
    @JoinColumn(name = "ID_UTILISATEUR", nullable = false, unique = true)
    private Utilisateur utilisateur;

    // Relation 1-N avec Location
    @OneToMany(mappedBy = "client")
    private List<Location> locations;

    // Getters et Setters
    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getTypeForfait() {
        return typeForfait;
    }

    public void setTypeForfait(String typeForfait) {
        this.typeForfait = typeForfait;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}