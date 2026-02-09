package ca.etsmtl.log660.cinema_backend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LOCATION")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq")
    @SequenceGenerator(name = "location_seq", sequenceName = "SEQ_LOCATION", allocationSize = 1)
    @Column(name = "ID_LOCATION")
    private Long idLocation;

    @Column(name = "DATE_LOCATION", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateLocation;

    @Column(name = "DATE_RETOUR_PREVUE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateRetourPrevue;

    @Column(name = "DATE_RETOUR_EFFECTIVE")
    @Temporal(TemporalType.DATE)
    private Date dateRetourEffective;

    // Relation N-1 avec Client
    @ManyToOne
    @JoinColumn(name = "ID_CLIENT", nullable = false)
    private Client client;

    // Relation N-1 avec Copie
    @ManyToOne
    @JoinColumn(name = "ID_COPIE", nullable = false)
    private Copie copie;

    // Getters et Setters
    public Long getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Long idLocation) {
        this.idLocation = idLocation;
    }

    public Date getDateLocation() {
        return dateLocation;
    }

    public void setDateLocation(Date dateLocation) {
        this.dateLocation = dateLocation;
    }

    public Date getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(Date dateRetourPrevue) {
        this.dateRetourPrevue = dateRetourPrevue;
    }

    public Date getDateRetourEffective() {
        return dateRetourEffective;
    }

    public void setDateRetourEffective(Date dateRetourEffective) {
        this.dateRetourEffective = dateRetourEffective;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Copie getCopie() {
        return copie;
    }

    public void setCopie(Copie copie) {
        this.copie = copie;
    }
}