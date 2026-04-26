package com.smartplanner.services;

import com.smartplanner.models.Tache;
import com.smartplanner.enums.PriorityLevel;
import com.smartplanner.enums.TaskType;
import com.smartplanner.utils.SimpleLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing tasks
 */
public class TaskService {
    private static final SimpleLogger logger = SimpleLogger.getLogger(TaskService.class);

    private List<Tache> taches;

    public TaskService() {
        this.taches = new ArrayList<>();
    }

    /**
     * Add a new task
     */
    public Tache ajouterTache(String utilisateurId, String description, int dureeMinutes,
                             PriorityLevel priorite, TaskType type) {
        if (description == null || description.isBlank()) {
            logger.warn("Task description cannot be empty");
            return null;
        }
        if (dureeMinutes <= 0) {
            logger.warn("Task duration must be positive");
            return null;
        }
        if (priorite == null) {
            priorite = PriorityLevel.MOYENNE;
        }
        if (type == null) {
            type = TaskType.PERSONNELLE;
        }

        Tache tache = new Tache(description, dureeMinutes, priorite, type);
        tache.setUtilisateurId(utilisateurId);
        taches.add(tache);
        logger.info("Task added for user {}: {}", utilisateurId, description);
        return tache;
    }

    /**
     * Get all tasks for a user
     */
    public List<Tache> obtenirTachesUtilisateur(String utilisateurId) {
        return taches.stream()
                .filter(t -> t.getUtilisateurId().equals(utilisateurId))
                .collect(Collectors.toList());
    }

    /**
     * Get pending tasks for a user
     */
    public List<Tache> obtenirTachesPendantes(String utilisateurId) {
        return taches.stream()
                .filter(t -> t.getUtilisateurId().equals(utilisateurId) && !t.isCompletee())
                .collect(Collectors.toList());
    }

    /**
     * Get completed tasks for a user
     */
    public List<Tache> obtenirTachesTerminees(String utilisateurId) {
        return taches.stream()
                .filter(t -> t.getUtilisateurId().equals(utilisateurId) && t.isCompletee())
                .collect(Collectors.toList());
    }

    /**
     * Get tasks by priority
     */
    public List<Tache> obtenirTachesParPriorite(String utilisateurId, PriorityLevel priorite) {
        return taches.stream()
                .filter(t -> t.getUtilisateurId().equals(utilisateurId) &&
                        t.getPriorite().equals(priorite))
                .collect(Collectors.toList());
    }

    /**
     * Get tasks by type
     */
    public List<Tache> obtenirTachesParType(String utilisateurId, TaskType type) {
        return taches.stream()
                .filter(t -> t.getUtilisateurId().equals(utilisateurId) &&
                        t.getType().equals(type))
                .collect(Collectors.toList());
    }

    /**
     * Mark task as completed
     */
    public void marquerTacheTerminee(String tacheId) {
        Tache tache = taches.stream()
                .filter(t -> t.getId().equals(tacheId))
                .findFirst()
                .orElse(null);

        if (tache != null) {
            tache.setCompletee(true);
            logger.info("Task {} marked as completed", tacheId);
        }
    }

    /**
     * Update task
     */
    public Tache mettreAJourTache(String tacheId, String description, int dureeMinutes,
                                 PriorityLevel priorite, TaskType type) {
        Tache tache = taches.stream()
                .filter(t -> t.getId().equals(tacheId))
                .findFirst()
                .orElse(null);

        if (tache != null) {
            tache.setDescription(description);
            tache.setDureeMinutes(dureeMinutes);
            tache.setPriorite(priorite);
            tache.setType(type);
            logger.info("Task {} updated", tacheId);
        }

        return tache;
    }

    /**
     * Delete task
     */
    public void supprimerTache(String tacheId) {
        taches.removeIf(t -> t.getId().equals(tacheId));
        logger.info("Task {} deleted", tacheId);
    }

    /**
     * Get task by ID
     */
    public Tache obtenirTache(String tacheId) {
        return taches.stream()
                .filter(t -> t.getId().equals(tacheId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Calculate total study time for a user
     */
    public int calculerTempsEtudeTotal(String utilisateurId) {
        return taches.stream()
                .filter(t -> t.getUtilisateurId().equals(utilisateurId) && !t.isCompletee())
                .mapToInt(Tache::getDureeMinutes)
                .sum();
    }

    public List<Tache> getAllTasks() {
        return new ArrayList<>(taches);
    }
}
