package com.smartplanner.models;

import com.smartplanner.enums.TimeSlotType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents constraints and obstacles in the schedule
 */
public class Contrainte implements Serializable {
    private String id;
    private String utilisateurId;
    private TimeSlotType type;
    private LocalDateTime dateHeure;
    private int dureeMinutes;
    private String description;
    private boolean estActive;

    public Contrainte() {
        this.id = UUID.randomUUID().toString();
        this.estActive = true;
    }

    public Contrainte(TimeSlotType type, LocalDateTime dateHeure, int dureeMinutes, String description) {
        this();
        this.type = type;
        this.dateHeure = dateHeure;
        this.dureeMinutes = dureeMinutes;
        this.description = description;
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

    public TimeSlotType getType() {
        return type;
    }

    public void setType(TimeSlotType type) {
        this.type = type;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public int getDureeMinutes() {
        return dureeMinutes;
    }

    public void setDureeMinutes(int dureeMinutes) {
        this.dureeMinutes = dureeMinutes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEstActive() {
        return estActive;
    }

    public void setEstActive(boolean estActive) {
        this.estActive = estActive;
    }

    @Override
    public String toString() {
        return "Contrainte{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", dateHeure=" + dateHeure +
                ", dureeMinutes=" + dureeMinutes +
                ", description='" + description + '\'' +
                ", estActive=" + estActive +
                '}';
    }
}
