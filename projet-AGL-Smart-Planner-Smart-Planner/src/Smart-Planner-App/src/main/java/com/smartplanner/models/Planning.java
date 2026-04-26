package com.smartplanner.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents a generated daily/weekly plan
 */
public class Planning implements Serializable {
    private String id;
    private String utilisateurId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private List<ElementPlanning> elements;
    private String statut; // "GENERE", "EN_COURS", "TERMINE", "ANNULE"
    private LocalDateTime dateGeneration;

    public Planning() {
        this.id = UUID.randomUUID().toString();
        this.elements = new ArrayList<>();
        this.statut = "GENERE";
        this.dateGeneration = LocalDateTime.now();
    }

    public Planning(String utilisateurId, LocalDate dateDebut, LocalDate dateFin) {
        this();
        this.utilisateurId = utilisateurId;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
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

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public List<ElementPlanning> getElements() {
        return elements;
    }

    public void setElements(List<ElementPlanning> elements) {
        this.elements = elements;
    }

    public void addElement(ElementPlanning element) {
        this.elements.add(element);
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LocalDateTime getDateGeneration() {
        return dateGeneration;
    }

    public void setDateGeneration(LocalDateTime dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    @Override
    public String toString() {
        return "Planning{" +
                "id='" + id + '\'' +
                ", utilisateurId='" + utilisateurId + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", statut='" + statut + '\'' +
                ", elements=" + elements.size() +
                '}';
    }
}
