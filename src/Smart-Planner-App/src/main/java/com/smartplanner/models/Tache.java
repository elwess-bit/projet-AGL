package com.smartplanner.models;

import com.smartplanner.enums.PriorityLevel;
import com.smartplanner.enums.TaskType;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a task to be scheduled
 */
public class Tache implements Serializable, Comparable<Tache> {
    private String id;
    private String description;
    private int dureeMinutes;
    private PriorityLevel priorite;
    private TaskType type;
    private String utilisateurId;
    private boolean completee;
    private String dateDebut;
    private String dateFin;

    public Tache() {
        this.id = UUID.randomUUID().toString();
        this.completee = false;
    }

    public Tache(String description, int dureeMinutes, PriorityLevel priorite, TaskType type) {
        this();
        this.description = description;
        this.dureeMinutes = dureeMinutes;
        this.priorite = priorite;
        this.type = type;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDureeMinutes() {
        return dureeMinutes;
    }

    public void setDureeMinutes(int dureeMinutes) {
        this.dureeMinutes = dureeMinutes;
    }

    public PriorityLevel getPriorite() {
        return priorite;
    }

    public void setPriorite(PriorityLevel priorite) {
        this.priorite = priorite;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public String getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(String utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public boolean isCompletee() {
        return completee;
    }

    public void setCompletee(boolean completee) {
        this.completee = completee;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public int compareTo(Tache other) {
        // Sort by priority (descending), then by type
        int priorityComparison = Integer.compare(other.priorite.getValue(), this.priorite.getValue());
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        return Integer.compare(other.type.ordinal(), this.type.ordinal());
    }

    @Override
    public String toString() {
        return "Tache{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", dureeMinutes=" + dureeMinutes +
                ", priorite=" + priorite +
                ", type=" + type +
                ", completee=" + completee +
                ", dateDebut='" + dateDebut + '\'' +
                ", dateFin='" + dateFin + '\'' +
                '}';
    }
}
