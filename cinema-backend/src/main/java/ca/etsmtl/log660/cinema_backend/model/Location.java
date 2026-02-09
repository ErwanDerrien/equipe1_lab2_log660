package ca.etsmtl.log660.cinema_backend.model;

import java.util.Date;

public class Location {

    private Long idLocation;

    private Date dateLocation;

    private Date dateRetourPrevue;

    private Date dateRetourEffective;

    // Relation N-1 avec Client
    private Client client;

    // Relation N-1 avec Copie
    private Copie copie;

    // Constructeur vide
    public Location() {
    }

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