package com.smartplanner.models;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a user (Student) of the Smart Daily Planner
 */
public class Utilisateur implements Serializable {
    private String id;
    private String nom;
    private String email;
    private String motDePasse;
    private String niveauEtude;

    public Utilisateur() {
        this.id = UUID.randomUUID().toString();
    }

    public Utilisateur(String nom, String email, String motDePasse, String niveauEtude) {
        this();
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.niveauEtude = niveauEtude;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(String niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", niveauEtude='" + niveauEtude + '\'' +
                '}';
    }
}
