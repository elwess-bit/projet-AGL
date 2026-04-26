package com.smartplanner.models;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

/**
 * Represents available time slots in the user's schedule
 */
public class Disponibilite implements Serializable {
    private String id;
    private String utilisateurId;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String jour; // Day of week
    private boolean estLibre; // Whether slot is available

    public Disponibilite() {
        this.id = UUID.randomUUID().toString();
        this.estLibre = true;
    }

    public Disponibilite(LocalTime heureDebut, LocalTime heureFin, String jour) {
        this();
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.jour = jour;
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

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public boolean isEstLibre() {
        return estLibre;
    }

    public void setEstLibre(boolean estLibre) {
        this.estLibre = estLibre;
    }

    /**
     * Calculate duration of availability in minutes
     */
    public int getDurationMinutes() {
        if (heureDebut == null || heureFin == null) {
            return 0;
        }
        return (int) java.time.temporal.ChronoUnit.MINUTES.between(heureDebut, heureFin);
    }

    @Override
    public String toString() {
        return "Disponibilite{" +
                "id='" + id + '\'' +
                ", jour='" + jour + '\'' +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                ", estLibre=" + estLibre +
                '}';
    }
}
