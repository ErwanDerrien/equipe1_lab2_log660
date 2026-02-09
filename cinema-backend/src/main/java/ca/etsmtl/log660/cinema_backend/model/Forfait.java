package ca.etsmtl.log660.cinema_backend.model;

public class Forfait {

    private String code;

    private String nom;

    private Double coutMensuel;

    private Integer locationsMax;

    private Integer dureeMaxJours;

    // Constructeur vide
    public Forfait() {
    }

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