package com.smartplanner.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant le tableau de bord de l'application
 */
public class TableauBord {
    private List<Statistique> historiqueStats;
    private Planning planningActuel;

    /**
     * Constructeur vide
     */
    public TableauBord() {
        this.historiqueStats = new ArrayList<>();
        this.planningActuel = null;
    }

    /**
     * Constructeur avec Planning
     * @param planningActuel le planning actuel
     */
    public TableauBord(Planning planningActuel) {
        this.historiqueStats = new ArrayList<>();
        this.planningActuel = planningActuel;
    }

    /**
     * Affiche la vue du planning actuel
     */
    public void afficherPlanningVue() {
        System.out.println("=== AFFICHAGE PLANNING ===");
        if (planningActuel != null) {
            System.out.println("Planning : " + planningActuel.getId());
        } else {
            System.out.println("Aucun planning actuel");
        }
    }

    /**
     * Marque une tâche comme complétée
     * @param idTache l'identifiant de la tâche
     */
    public void marquerTacheFaite(int idTache) {
        System.out.println("Tâche " + idTache + " marquée comme faite");
    }

    // Getters et Setters

    public List<Statistique> getHistoriqueStats() {
        return historiqueStats;
    }

    public void setHistoriqueStats(List<Statistique> historiqueStats) {
        this.historiqueStats = historiqueStats;
    }

    public void ajouterStatistique(Statistique statistique) {
        this.historiqueStats.add(statistique);
    }

    public Planning getPlanningActuel() {
        return planningActuel;
    }

    public void setPlanningActuel(Planning planningActuel) {
        this.planningActuel = planningActuel;
    }
}
