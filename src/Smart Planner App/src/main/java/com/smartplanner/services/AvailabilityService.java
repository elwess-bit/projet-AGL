package com.smartplanner.services;

import com.smartplanner.models.Disponibilite;
import com.smartplanner.models.Contrainte;
import com.smartplanner.enums.TimeSlotType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing availability and constraints
 */
public class AvailabilityService {
    private static final Logger logger = LoggerFactory.getLogger(AvailabilityService.class);

    private List<Disponibilite> disponibilites;
    private List<Contrainte> contraintes;

    public AvailabilityService() {
        this.disponibilites = new ArrayList<>();
        this.contraintes = new ArrayList<>();
    }

    /**
     * Add availability slot
     */
    public Disponibilite ajouterDisponibilite(String utilisateurId, LocalTime heureDebut,
                                             LocalTime heureFin, String jour) {
        if (heureDebut == null || heureFin == null || !heureDebut.isBefore(heureFin)) {
            logger.warn("Invalid availability time range: {} - {}", heureDebut, heureFin);
            return null;
        }

        List<Disponibilite> existing = obtenirDisponibilitesJour(utilisateurId, jour);
        for (Disponibilite prior : existing) {
            if (prior.isEstLibre() && heuresChevauchent(prior.getHeureDebut(), prior.getHeureFin(), heureDebut, heureFin)) {
                logger.warn("Availability overlaps with existing slot on {}: {} - {}", jour, prior.getHeureDebut(), prior.getHeureFin());
                return null;
            }
        }

        Disponibilite disp = new Disponibilite(heureDebut, heureFin, jour);
        disp.setUtilisateurId(utilisateurId);
        disponibilites.add(disp);
        logger.info("Availability added for user {} on {}: {} to {}", utilisateurId, jour, heureDebut, heureFin);
        return disp;
    }

    private boolean heuresChevauchent(LocalTime debut1, LocalTime fin1, LocalTime debut2, LocalTime fin2) {
        return debut1.isBefore(fin2) && debut2.isBefore(fin1);
    }

    /**
     * Get availability for a user
     */
    public List<Disponibilite> obtenirDisponibilites(String utilisateurId) {
        return disponibilites.stream()
                .filter(d -> d.getUtilisateurId().equals(utilisateurId))
                .collect(Collectors.toList());
    }

    /**
     * Get availability for a specific day
     */
    public List<Disponibilite> obtenirDisponibilitesJour(String utilisateurId, String jour) {
        return disponibilites.stream()
                .filter(d -> d.getUtilisateurId().equals(utilisateurId) &&
                        d.getJour().equalsIgnoreCase(jour))
                .collect(Collectors.toList());
    }

    /**
     * Add constraint (transportation delay, schedule change, etc.)
     */
    public Contrainte ajouterContrainte(String utilisateurId, TimeSlotType type,
                                       LocalDateTime dateHeure, int dureeMinutes, String description) {
        Contrainte contrainte = new Contrainte(type, dateHeure, dureeMinutes, description);
        contrainte.setUtilisateurId(utilisateurId);
        contraintes.add(contrainte);
        logger.info("Constraint added for user {} at {}: {} ({}min)", utilisateurId, dateHeure, type, dureeMinutes);
        return contrainte;
    }

    /**
     * Get active constraints for a user
     */
    public List<Contrainte> obtenirContraintes(String utilisateurId) {
        return contraintes.stream()
                .filter(c -> c.getUtilisateurId().equals(utilisateurId) && c.isEstActive())
                .collect(Collectors.toList());
    }

    /**
     * Get constraints by type
     */
    public List<Contrainte> obtenirContraintesParType(String utilisateurId, TimeSlotType type) {
        return contraintes.stream()
                .filter(c -> c.getUtilisateurId().equals(utilisateurId) &&
                        c.getType().equals(type) && c.isEstActive())
                .collect(Collectors.toList());
    }

    /**
     * Deactivate constraint
     */
    public void desactiverContrainte(String contrainteId) {
        Contrainte contrainte = contraintes.stream()
                .filter(c -> c.getId().equals(contrainteId))
                .findFirst()
                .orElse(null);

        if (contrainte != null) {
            contrainte.setEstActive(false);
            logger.debug("Constraint {} deactivated", contrainteId);
        }
    }

    /**
     * Delete availability slot
     */
    public void supprimerDisponibilite(String disponibiliteId) {
        disponibilites.removeIf(d -> d.getId().equals(disponibiliteId));
        logger.debug("Availability {} deleted", disponibiliteId);
    }

    /**
     * Update availability
     */
    public Disponibilite mettreAJourDisponibilite(String disponibiliteId, LocalTime heureDebut,
                                                  LocalTime heureFin, String jour) {
        Disponibilite disp = disponibilites.stream()
                .filter(d -> d.getId().equals(disponibiliteId))
                .findFirst()
                .orElse(null);

        if (disp != null) {
            disp.setHeureDebut(heureDebut);
            disp.setHeureFin(heureFin);
            disp.setJour(jour);
            logger.debug("Availability {} updated", disponibiliteId);
        }

        return disp;
    }

    /**
     * Calculate total available hours per day
     */
    public int calculerHeuresDisponiblesParJour(String utilisateurId) {
        return disponibilites.stream()
                .filter(d -> d.getUtilisateurId().equals(utilisateurId) && d.isEstLibre())
                .mapToInt(Disponibilite::getDurationMinutes)
                .sum();
    }

    public List<Disponibilite> getAllDisponibilites() {
        return new ArrayList<>(disponibilites);
    }

    public List<Contrainte> getAllContraintes() {
        return new ArrayList<>(contraintes);
    }
}
