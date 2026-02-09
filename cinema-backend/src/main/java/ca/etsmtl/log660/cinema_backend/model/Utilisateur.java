package ca.etsmtl.log660.cinema_backend.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UTILISATEUR")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utilisateur_seq")
    @SequenceGenerator(name = "utilisateur_seq", sequenceName = "SEQ_UTILISATEUR", allocationSize = 1)
    @Column(name = "ID_UTILISATEUR")
    private Long idUtilisateur;

    @Column(name = "NOM", length = 50, nullable = false)
    private String nom;

    @Column(name = "PRENOM", length = 50, nullable = false)
    private String prenom;

    @Column(name = "COURRIEL", length = 100, nullable = false, unique = true)
    private String courriel;

    @Column(name = "TELEPHONE", length = 20, nullable = false)
    private String telephone;

    @Column(name = "ADRESSE_CIVIQUE", length = 10, nullable = false)
    private String adresseCivique;

    @Column(name = "RUE", length = 100, nullable = false)
    private String rue;

    @Column(name = "VILLE", length = 50, nullable = false)
    private String ville;

    @Column(name = "PROVINCE", length = 50, nullable = false)
    private String province;

    @Column(name = "CODE_POSTAL", length = 10, nullable = false)
    private String codePostal;

    @Column(name = "DATE_NAISSANCE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    @Column(name = "MOT_DE_PASSE", length = 100, nullable = false)
    private String motDePasse;

    // Getters et Setters
    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresseCivique() {
        return adresseCivique;
    }

    public void setAdresseCivique(String adresseCivique) {
        this.adresseCivique = adresseCivique;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}