package ca.etsmtl.log660.cinema_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "FORFAIT")
public class Forfait {

    @Id
    @Column(name = "CODE", length = 1)
    private String code;

    @Column(name = "NOM", length = 20, nullable = false)
    private String nom;

    @Column(name = "COUT_MENSUEL", precision = 5, scale = 2, nullable = false)
    private Double coutMensuel;

    @Column(name = "LOCATIONS_MAX", precision = 3, nullable = false)
    private Integer locationsMax;

    @Column(name = "DUREE_MAX_JOURS", precision = 4, nullable = false)
    private Integer dureeMaxJours;

    // Getters et Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getCoutMensuel() {
        return coutMensuel;
    }

    public void setCoutMensuel(Double coutMensuel) {
        this.coutMensuel = coutMensuel;
    }

    public Integer getLocationsMax() {
        return locationsMax;
    }

    public void setLocationsMax(Integer locationsMax) {
        this.locationsMax = locationsMax;
    }

    public Integer getDureeMaxJours() {
        return dureeMaxJours;
    }

    public void setDureeMaxJours(Integer dureeMaxJours) {
        this.dureeMaxJours = dureeMaxJours;
    }
}