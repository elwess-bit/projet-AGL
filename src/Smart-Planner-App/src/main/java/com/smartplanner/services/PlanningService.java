package com.smartplanner.services;

import com.smartplanner.models.*;
import com.smartplanner.enums.PriorityLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Core service for generating and optimizing schedules
 */
public class PlanningService {
    private static final Logger logger = LoggerFactory.getLogger(PlanningService.class);

    private List<Planning> plannings;
    private List<Tache> taches;
    private List<Disponibilite> disponibilites;
    private List<Contrainte> contraintes;
    
    // References to other services (for data synchronization)
    private TaskService taskService;
    private AvailabilityService availabilityService;

    public PlanningService() {
        this.plannings = new ArrayList<>();
        this.taches = new ArrayList<>();
        this.disponibilites = new ArrayList<>();
        this.contraintes = new ArrayList<>();
    }
    
    /**
     * Set task service reference for data synchronization
     */
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
        // Sync existing tasks
        if (taskService != null) {
            this.taches.addAll(taskService.getAllTasks());
        }
    }
    
    /**
     * Set availability service reference for data synchronization
     */
    public void setAvailabilityService(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
        // Sync existing data
        if (availabilityService != null) {
            this.disponibilites.addAll(availabilityService.getAllDisponibilites());
            this.contraintes.addAll(availabilityService.getAllContraintes());
        }
    }
    
    /**
     * Add a task to planning service
     */
    public void addTask(Tache task) {
        if (task != null && !taches.contains(task)) {
            taches.add(task);
        }
    }
    
    /**
     * Add availability to planning service
     */
    public void addDisponibilite(Disponibilite disp) {
        if (disp != null && !disponibilites.contains(disp)) {
            disponibilites.add(disp);
        }
    }
    
    /**
     * Add constraint to planning service
     */
    public void addContrainte(Contrainte constraint) {
        if (constraint != null && !contraintes.contains(constraint)) {
            contraintes.add(constraint);
        }
    }

    /**
     * Generate optimized schedule for a user
     */
    public Planning genererPlanning(String utilisateurId, LocalDate dateDebut, LocalDate dateFin,
                                    List<Tache> tachesUtilisateur, List<Disponibilite> disponibilitesUtilisateur,
                                    List<Contrainte> contraintesUtilisateur, Preference preference) {
        logger.info("Generating planning for user {} from {} to {}", utilisateurId, dateDebut, dateFin);

        Planning planning = new Planning(utilisateurId, dateDebut, dateFin);

        List<Tache> tachesSortees = tachesUtilisateur.stream()
                .filter(t -> !t.isCompletee())
                .sorted()
                .collect(Collectors.toList());

        List<ElementPlanning> elementsPlanning = assignerTachesAuxCreneaux(
                tachesSortees,
                disponibilitesUtilisateur,
                contraintesUtilisateur,
                dateDebut,
                dateFin,
                preference
        );

        planning.setElements(elementsPlanning);
        planning.setStatut("GENERE");
        plannings.add(planning);

        logger.info("Planning generated with {} elements", elementsPlanning.size());
        return planning;
    }

    /**
     * Assign tasks to available time slots using optimization algorithm
     */
    private List<ElementPlanning> assignerTachesAuxCreneaux(List<Tache> taches,
                                                            List<Disponibilite> disponibilites,
                                                            List<Contrainte> contraintes,
                                                            LocalDate dateDebut,
                                                            LocalDate dateFin,
                                                            Preference preference) {
        List<ElementPlanning> elements = new ArrayList<>();

        Map<LocalDate, List<TimeSlot>> availableSlots = buildAvailableSlots(disponibilites, contraintes, dateDebut, dateFin);

        for (Tache tache : taches) {
            TimeSlot bestSlot = findBestSlot(tache, availableSlots, preference);

            if (bestSlot != null) {
                ElementPlanning element = new ElementPlanning();
                element.setTacheId(tache.getId());
                element.setHeureDebut(LocalDateTime.of(bestSlot.date, bestSlot.heureDebut));
                element.setHeureFin(LocalDateTime.of(bestSlot.date, bestSlot.heureDebut.plusMinutes(tache.getDureeMinutes())));
                element.setDescription(tache.getDescription());
                element.setPriorite(tache.getPriorite().getValue());
                element.setStatut("PLANIFIEE");

                elements.add(element);

                tache.setDateDebut(element.getHeureDebut().toString());
                tache.setDateFin(element.getHeureFin().toString());

                removeSlot(bestSlot, availableSlots, tache.getDureeMinutes());

                logger.debug("Task {} scheduled from {} to {}", tache.getId(),
                        element.getHeureDebut(), element.getHeureFin());
            } else {
                logger.warn("Could not find slot for task {}", tache.getId());
            }
        }

        return elements;
    }

    /**
     * Build map of available time slots for each day, considering constraints
     */
    private Map<LocalDate, List<TimeSlot>> buildAvailableSlots(List<Disponibilite> disponibilites,
                                                               List<Contrainte> contraintes,
                                                               LocalDate dateDebut,
                                                               LocalDate dateFin) {
        Map<LocalDate, List<TimeSlot>> slots = new TreeMap<>();

        for (Disponibilite disp : disponibilites) {
            if (disp.isEstLibre()) {
                LocalDate date = dateDebut;
                while (!date.isAfter(dateFin)) {
                    if (date.getDayOfWeek().toString().equalsIgnoreCase(disp.getJour())) {
                        TimeSlot slot = new TimeSlot(date, disp.getHeureDebut(), disp.getHeureFin());
                        slots.computeIfAbsent(date, k -> new ArrayList<>()).add(slot);
                    }
                    date = date.plusDays(1);
                }
            }
        }

        for (Contrainte contrainte : contraintes) {
            if (contrainte.isEstActive()) {
                LocalDate date = contrainte.getDateHeure().toLocalDate();
                LocalTime start = contrainte.getDateHeure().toLocalTime();
                LocalTime end = start.plusMinutes(contrainte.getDureeMinutes());

                if (slots.containsKey(date)) {
                    List<TimeSlot> daySlots = new ArrayList<>(slots.get(date));
                    List<TimeSlot> filtered = new ArrayList<>();

                    for (TimeSlot slot : daySlots) {
                        if (!intervalsOverlap(slot.heureDebut, slot.heureFin, start, end)) {
                            filtered.add(slot);
                        } else {
                            if (slot.heureDebut.isBefore(start)) {
                                filtered.add(new TimeSlot(date, slot.heureDebut, start));
                            }
                            if (end.isBefore(slot.heureFin)) {
                                filtered.add(new TimeSlot(date, end, slot.heureFin));
                            }
                        }
                    }

                    slots.put(date, filtered);
                }
            }
        }

        return slots;
    }

    private boolean intervalsOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    /**
     * Find best available slot for a task
     */
    private TimeSlot findBestSlot(Tache tache, Map<LocalDate, List<TimeSlot>> availableSlots, Preference preference) {
        return availableSlots.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream())
                .filter(slot -> slot.durationMinutes() >= tache.getDureeMinutes())
                .sorted((s1, s2) -> {
                    int score1 = scoreForSlot(tache, s1, preference);
                    int score2 = scoreForSlot(tache, s2, preference);
                    if (score1 != score2) {
                        return Integer.compare(score2, score1);
                    }
                    return s1.heureDebut.compareTo(s2.heureDebut);
                })
                .findFirst()
                .orElse(null);
    }

    private int scoreForSlot(Tache tache, TimeSlot slot, Preference preference) {
        int score = 0;
        if (tache.getPriorite() != null) {
            score += tache.getPriorite().getValue() * 10;
        }

        if (tache.getType() != null && tache.getType().requiresConcentration()) {
            String energy = getEnergyForSlot(slot, preference);
            if ("ELEVÉ".equalsIgnoreCase(energy)) {
                score += 20;
            } else if ("MOYEN".equalsIgnoreCase(energy)) {
                score += 10;
            }
        }

        if (isMorning(slot.heureDebut)) {
            score += 5;
        }

        return score;
    }

    private String getEnergyForSlot(TimeSlot slot, Preference preference) {
        if (isMorning(slot.heureDebut)) {
            return preference.getEnergieMatin();
        }
        if (isAfternoon(slot.heureDebut)) {
            return preference.getEnergieApresMidi();
        }
        return preference.getEnergieSoir();
    }

    private boolean isMorning(LocalTime time) {
        return time.getHour() >= 6 && time.getHour() < 12;
    }

    private boolean isAfternoon(LocalTime time) {
        return time.getHour() >= 12 && time.getHour() < 18;
    }

    private void removeSlot(TimeSlot slot, Map<LocalDate, List<TimeSlot>> availableSlots, int durationMinutes) {
        if (availableSlots.containsKey(slot.date)) {
            List<TimeSlot> daySlots = new ArrayList<>(availableSlots.get(slot.date));
            daySlots.remove(slot);
            LocalTime newStart = slot.heureDebut.plusMinutes(durationMinutes);
            if (newStart.isBefore(slot.heureFin)) {
                daySlots.add(new TimeSlot(slot.date, newStart, slot.heureFin));
            }
            availableSlots.put(slot.date, daySlots);
        }
    }

    /**
     * Inner class to represent a time slot
     */
    private static class TimeSlot {
        LocalDate date;
        LocalTime heureDebut;
        LocalTime heureFin;

        TimeSlot(LocalDate date, LocalTime heureDebut, LocalTime heureFin) {
            this.date = date;
            this.heureDebut = heureDebut;
            this.heureFin = heureFin;
        }

        int durationMinutes() {
            return (int) java.time.temporal.ChronoUnit.MINUTES.between(heureDebut, heureFin);
        }
    }

    // Getters
    public List<Planning> getPlannings() {
        return plannings;
    }

    public Planning getPlanningById(String id) {
        return plannings.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Planning> getPlanningsByUser(String userId) {
        return plannings.stream()
                .filter(p -> p.getUtilisateurId().equals(userId))
                .collect(Collectors.toList());
    }
}