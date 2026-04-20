package com.smartplanner.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a single scheduled element in a planning (task or break)
 */
public class ElementPlanning implements Serializable {
    private String id;
    private String planningId;
    private String tacheId;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    private String statut; // "PLANIFIEE", "EN_COURS", "TERMINEE", "SAUTEE"
    private String description;
    private int priorite;

    public ElementPlanning() {
        this.id = UUID.randomUUID().toString();
        this.statut = "PLANIFIEE";
    }

    public ElementPlanning(String tacheId, LocalDateTime heureDebut, LocalDateTime heureFin) {
        this();
        this.tacheId = tacheId;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanningId() {
        return planningId;
    }

    public void setPlanningId(String planningId) {
        this.planningId = planningId;
    }

    public String getTacheId() {
        return tacheId;
    }

    public void setTacheId(String tacheId) {
        this.tacheId = tacheId;
    }

    public LocalDateTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalDateTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalDateTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalDateTime heureFin) {
        this.heureFin = heureFin;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriorite() {
        return priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    /**
     * Get duration in minutes
     */
    public long getDurationMinutes() {
        if (heureDebut == null || heureFin == null) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.MINUTES.between(heureDebut, heureFin);
    }

    @Override
    public String toString() {
        return "ElementPlanning{" +
                "id='" + id + '\'' +
                ", tacheId='" + tacheId + '\'' +
                ", heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                ", statut='" + statut + '\'' +
                '}';
    }
}
