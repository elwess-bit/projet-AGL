package com.smartplanner;

import com.smartplanner.services.*;
import com.smartplanner.models.*;
import com.smartplanner.enums.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Main Application class - Smart Daily Planner
 * Orchestrates all services for intelligent scheduling
 */
public class SmartPlannerApplication {
    private static final Logger logger = LoggerFactory.getLogger(SmartPlannerApplication.class);

    private UserService userService;
    private TaskService taskService;
    private AvailabilityService availabilityService;
    private PlanningService planningService;
    private NotificationService notificationService;
    private PreferenceService preferenceService;

    public SmartPlannerApplication() {
        this.userService = new UserService();
        this.taskService = new TaskService();
        this.availabilityService = new AvailabilityService();
        this.planningService = new PlanningService();
        this.notificationService = new NotificationService();
        this.preferenceService = new PreferenceService();
        
        // Link services together for data sharing
        linkServices();
        
        logger.info("Smart Planner Application initialized");
    }
    
    /**
     * Link services to share data
     */
    private void linkServices() {
        // Pass task service reference to planning service
        planningService.setTaskService(taskService);
        // Pass availability service reference to planning service
        planningService.setAvailabilityService(availabilityService);
    }

    /**
     * Register new user
     */
    public Utilisateur registerUser(String nom, String email, String motDePasse, String niveauEtude) {
        Utilisateur user = userService.creerUtilisateur(nom, email, motDePasse, niveauEtude);
        if (user != null) {
            preferenceService.sauvegarderPreference(new Preference(user.getId()));
            logger.info("User registered: {}", user.getNom());
        }
        return user;
    }

    public Utilisateur loginUser(String email, String motDePasse) {
        return userService.authentifierUtilisateur(email, motDePasse);
    }

    /**
     * Add task for user
     */
    public Tache addTask(String userId, String description, int durationMinutes,
                        PriorityLevel priority, TaskType type) {
        Tache task = taskService.ajouterTache(userId, description, durationMinutes, priority, type);
        // Also add to planning service's task list
        if (task != null && planningService != null) {
            planningService.addTask(task);
        }
        return task;
    }

    /**
     * Add availability slot
     */
    public Disponibilite addAvailability(String userId, LocalTime startTime, LocalTime endTime, String day) {
        Disponibilite availability = availabilityService.ajouterDisponibilite(userId, startTime, endTime, day);
        if (availability != null && planningService != null) {
            planningService.addDisponibilite(availability);
        }
        return availability;
    }

    /**
     * Add constraint
     */
    public Contrainte addConstraint(String userId, TimeSlotType type, LocalDateTime dateTime,
                                   int durationMinutes, String description) {
        Contrainte constraint = availabilityService.ajouterContrainte(userId, type, dateTime, durationMinutes, description);
        if (constraint != null && planningService != null) {
            planningService.addContrainte(constraint);
        }
        return constraint;
    }

    /**
     * Generate optimized schedule
     */
    public Planning generateSchedule(String userId, LocalDate startDate, LocalDate endDate) {
        List<Tache> userTasks = taskService.obtenirTachesUtilisateur(userId);
        List<Disponibilite> userAvailability = availabilityService.obtenirDisponibilites(userId);
        List<Contrainte> userConstraints = availabilityService.obtenirContraintes(userId);
        Preference pref = preferenceService.obtenirPreference(userId);

        if (userTasks.isEmpty()) {
            logger.warn("No tasks found for user {}. Cannot generate schedule.", userId);
            notificationService.envoyerNotification(userId, "Pas de tâches",
                    "Veuillez ajouter des tâches avant de générer un planning", "INFO");
            return null;
        }

        if (userAvailability.isEmpty()) {
            logger.warn("No availability found for user {}. Cannot generate schedule.", userId);
            notificationService.envoyerNotification(userId, "Pas de disponibilités",
                    "Veuillez définir votre disponibilité avant de générer un planning", "INFO");
            return null;
        }

        Planning planning = planningService.genererPlanning(userId, startDate, endDate,
                userTasks, userAvailability, userConstraints, pref);

        if (planning != null) {
            notificationService.envoyerNotification(userId, "Planning généré",
                    "Votre planning a été généré avec " + planning.getElements().size() + " tâches planifiées",
                    "PLANNING_GENERE");
        }

        return planning;
    }

    /**
     * Send task reminder
     */
    public void sendTaskReminder(String userId, String taskName) {
        Preference pref = preferenceService.obtenirPreference(userId);
        if (pref.isNotificationsActivees()) {
            notificationService.envoyerRappelTache(userId, taskName, pref.getMinutesRappelAvant());
        }
    }

    /**
     * Handle transportation delay
     */
    public void handleTransportationDelay(String userId, int delayMinutes, String description) {
        if (delayMinutes <= 0) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        addConstraint(userId, TimeSlotType.RETARD_TRANSPORT, now, delayMinutes, description);
        
        Preference pref = preferenceService.obtenirPreference(userId);
        if (pref.isActiverNotificationsRetard()) {
            notificationService.envoyerNotificationRetard(userId, description, delayMinutes);
        }

        Planning lastPlanning = getUserPlanning(userId);
        if (lastPlanning != null) {
            generateSchedule(userId, lastPlanning.getDateDebut(), lastPlanning.getDateFin());
            notificationService.envoyerNotification(userId, "Rescheduling",
                    "Votre planning a été ajusté suite au retard de transport.", "RESCHEDULE");
        }
    }

    /**
     * Mark task as completed
     */
    public void completeTask(String taskId) {
        taskService.marquerTacheTerminee(taskId);
        logger.info("Task {} marked as completed", taskId);
    }

    /**
     * Get user's planning
     */
    public Planning getUserPlanning(String userId) {
        List<Planning> userPlannings = planningService.getPlanningsByUser(userId);
        return userPlannings.isEmpty() ? null : userPlannings.get(userPlannings.size() - 1);
    }

    /**
     * Get user's unread notifications
     */
    public List<Notification> getUnreadNotifications(String userId) {
        return notificationService.getNotificationsNonLues(userId);
    }

    /**
     * Display user's dashboard
     */
    public void displayUserDashboard(String userId) {
        Utilisateur user = userService.obtenirUtilisateur(userId);
        if (user == null) {
            logger.error("User not found: {}", userId);
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("SMART DAILY PLANNER - DASHBOARD");
        System.out.println("=".repeat(60));
        System.out.println("Utilisateur : " + user.getNom());
        System.out.println("Email : " + user.getEmail());
        System.out.println("Niveau : " + user.getNiveauEtude());
        System.out.println();

        // Display pending tasks
        List<Tache> tasks = taskService.obtenirTachesPendantes(userId);
        System.out.println("--- Tâches en attente : " + tasks.size() + " ---");
        for (Tache task : tasks) {
            String priorityLabel = task.getPriorite() == PriorityLevel.HAUTE ? "HAUTE" :
                                   (task.getPriorite() == PriorityLevel.MOYENNE ? "MOYENNE" : "BASSE");
            System.out.println("  • " + task.getDescription() + " (" + task.getDureeMinutes() + " min) [Priorité: " + priorityLabel + "]");
        }
        System.out.println();

        // Display availability
        List<Disponibilite> availabilities = availabilityService.obtenirDisponibilites(userId);
        System.out.println("--- Disponibilités : " + availabilities.size() + " ---");
        for (Disponibilite disp : availabilities) {
            System.out.println("  • " + disp.getJour() + " : " + disp.getHeureDebut() + " - " + disp.getHeureFin());
        }
        System.out.println();

        // Display planning if exists
        Planning planning = getUserPlanning(userId);
        if (planning != null && !planning.getElements().isEmpty()) {
            System.out.println("--- Planning actuel : " + planning.getElements().size() + " tâches planifiées ---");
            for (ElementPlanning elem : planning.getElements().stream().limit(5).toList()) {
                System.out.println("  • " + elem.getDescription() + " : " + 
                        elem.getHeureDebut().toLocalTime() + " - " + elem.getHeureFin().toLocalTime());
            }
        } else {
            System.out.println("--- Aucun planning généré ---");
        }
        System.out.println();

        // Display unread notifications
        List<Notification> notifications = getUnreadNotifications(userId);
        System.out.println("--- Notifications non lues : " + notifications.size() + " ---");
        for (Notification notif : notifications.stream().limit(3).toList()) {
            System.out.println("  • " + notif.getTitre() + " : " + notif.getMessage());
        }

        System.out.println("=".repeat(60) + "\n");
    }

    // Getters for services
    public UserService getUserService() {
        return userService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public AvailabilityService getAvailabilityService() {
        return availabilityService;
    }

    public PlanningService getPlanningService() {
        return planningService;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public PreferenceService getPreferenceService() {
        return preferenceService;
    }
}