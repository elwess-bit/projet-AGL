package com.smartplanner.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents notifications sent to users
 */
public class Notification implements Serializable {
    private String id;
    private String utilisateurId;
    private String titre;
    private String message;
    private LocalDateTime dateHeure;
    private boolean estLue;
    private String typeMessage; // "TACHE", "RETARD", "CHANGEMENT", etc.

    public Notification() {
        this.id = UUID.randomUUID().toString();
        this.estLue = false;
        this.dateHeure = LocalDateTime.now();
    }

    public Notification(String utilisateurId, String titre, String message, String typeMessage) {
        this();
        this.utilisateurId = utilisateurId;
        this.titre = titre;
        this.message = message;
        this.typeMessage = typeMessage;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(String utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public boolean isEstLue() {
        return estLue;
    }

    public void setEstLue(boolean estLue) {
        this.estLue = estLue;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(String typeMessage) {
        this.typeMessage = typeMessage;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", titre='" + titre + '\'' +
                ", message='" + message + '\'' +
                ", dateHeure=" + dateHeure +
                ", estLue=" + estLue +
                '}';
    }
}
