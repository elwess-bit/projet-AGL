package com.smartplanner.models;

import java.io.Serializable;
import java.util.UUID;

/**
 * Stores user preferences for scheduling
 */
public class Preference implements Serializable {
    private String id;
    private String utilisateurId;
    private boolean notificationsActivees;
    private int minutesRappelAvant;
    private String theme; // "CLAIR", "SOMBRE"
    private String languePrefere; // "FRANCAIS", "ARABE", "ANGLAIS"
    private int tempsEtudeIdealParJour; // in minutes
    private boolean activerNotificationsRetard;
    private String energieMatin; // ELEVÉ, MOYEN, FAIBLE
    private String energieApresMidi; // ELEVÉ, MOYEN, FAIBLE
    private String energieSoir; // ELEVÉ, MOYEN, FAIBLE

    public Preference() {
        this.id = UUID.randomUUID().toString();
        this.notificationsActivees = true;
        this.minutesRappelAvant = 15;
        this.theme = "CLAIR";
        this.languePrefere = "FRANCAIS";
        this.tempsEtudeIdealParJour = 240; // 4 hours
        this.activerNotificationsRetard = true;
        this.energieMatin = "ELEVÉ";
        this.energieApresMidi = "MOYEN";
        this.energieSoir = "FAIBLE";
    }

    public Preference(String utilisateurId) {
        this();
        this.utilisateurId = utilisateurId;
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

    public boolean isNotificationsActivees() {
        return notificationsActivees;
    }

    public void setNotificationsActivees(boolean notificationsActivees) {
        this.notificationsActivees = notificationsActivees;
    }

    public int getMinutesRappelAvant() {
        return minutesRappelAvant;
    }

    public void setMinutesRappelAvant(int minutesRappelAvant) {
        this.minutesRappelAvant = minutesRappelAvant;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLanguePrefere() {
        return languePrefere;
    }

    public void setLanguePrefere(String languePrefere) {
        this.languePrefere = languePrefere;
    }

    public int getTempsEtudeIdealParJour() {
        return tempsEtudeIdealParJour;
    }

    public void setTempsEtudeIdealParJour(int tempsEtudeIdealParJour) {
        this.tempsEtudeIdealParJour = tempsEtudeIdealParJour;
    }

    public String getEnergieMatin() {
        return energieMatin;
    }

    public void setEnergieMatin(String energieMatin) {
        this.energieMatin = energieMatin;
    }

    public String getEnergieApresMidi() {
        return energieApresMidi;
    }

    public void setEnergieApresMidi(String energieApresMidi) {
        this.energieApresMidi = energieApresMidi;
    }

    public String getEnergieSoir() {
        return energieSoir;
    }

    public void setEnergieSoir(String energieSoir) {
        this.energieSoir = energieSoir;
    }

    public boolean isActiverNotificationsRetard() {
        return activerNotificationsRetard;
    }

    public void setActiverNotificationsRetard(boolean activerNotificationsRetard) {
        this.activerNotificationsRetard = activerNotificationsRetard;
    }

    @Override
    public String toString() {
        return "Preference{" +
                "id='" + id + '\'' +
                ", notificationsActivees=" + notificationsActivees +
                ", theme='" + theme + '\'' +
                ", languePrefere='" + languePrefere + '\'' +
                ", energieMatin='" + energieMatin + '\'' +
                ", energieApresMidi='" + energieApresMidi + '\'' +
                ", energieSoir='" + energieSoir + '\'' +
                '}';
    }
}
