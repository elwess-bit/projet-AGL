package com.smartplanner.models;

/**
 * Classe représentant les statistiques de planification
 */
public class Statistique {
    private int nbTachesTotal;
    private int nbTachesCompletees;
    private double tauxCompletion;
    private String date;

    /**
     * Constructeur vide
     */
    public Statistique() {
        this.nbTachesTotal = 0;
        this.nbTachesCompletees = 0;
        this.tauxCompletion = 0.0;
        this.date = "";
    }

    /**
     * Constructeur avec paramètres
     * @param nbTachesTotal nombre total de tâches
     * @param nbTachesCompletees nombre de tâches complétées
     * @param date date des statistiques
     */
    public Statistique(int nbTachesTotal, int nbTachesCompletees, String date) {
        this.nbTachesTotal = nbTachesTotal;
        this.nbTachesCompletees = nbTachesCompletees;
        this.date = date;
        this.tauxCompletion = 0.0;
        calculerTauxCompletion();
    }

    /**
     * Calcule le taux de completion des tâches
     * tauxCompletion = (nbTachesCompletees / nbTachesTotal) * 100
     */
    public void calculerTauxCompletion() {
        if (nbTachesTotal > 0) {
            this.tauxCompletion = (double) nbTachesCompletees / nbTachesTotal * 100;
        } else {
            this.tauxCompletion = 0.0;
        }
    }

    /**
     * Affiche les statistiques en console
     */
    public void afficherStatistiques() {
        System.out.println("=== STATISTIQUES ===");
        System.out.println("Date : " + date);
        System.out.println("Nombre total de tâches : " + nbTachesTotal);
        System.out.println("Nombre de tâches complétées : " + nbTachesCompletees);
        System.out.println("Taux de completion : " + String.format("%.2f", tauxCompletion) + "%");
        System.out.println("Progression : " + construireBarreProgression(tauxCompletion) + " " + String.format("%.0f", tauxCompletion) + "%");
    }

    /**
     * Construit une barre de progression visuelle à partir du taux de completion
     */
    private String construireBarreProgression(double pourcentage) {
        int longueurBarre = 6;
        int remplissage = (int) Math.round((pourcentage / 100.0) * longueurBarre);
        remplissage = Math.max(0, Math.min(remplissage, longueurBarre));

        StringBuilder barre = new StringBuilder();
        barre.append('[');
        for (int i = 0; i < remplissage; i++) {
            barre.append('█');
        }
        for (int i = remplissage; i < longueurBarre; i++) {
            barre.append('░');
        }
        barre.append(']');
        return barre.toString();
    }

    // Getters et Setters

    public int getNbTachesTotal() {
        return nbTachesTotal;
    }

    public void setNbTachesTotal(int nbTachesTotal) {
        this.nbTachesTotal = nbTachesTotal;
    }

    public int getNbTachesCompletees() {
        return nbTachesCompletees;
    }

    public void setNbTachesCompletees(int nbTachesCompletees) {
        this.nbTachesCompletees = nbTachesCompletees;
    }

    public double getTauxCompletion() {
        return tauxCompletion;
    }

    public void setTauxCompletion(double tauxCompletion) {
        this.tauxCompletion = tauxCompletion;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
