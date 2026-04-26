package com.smartplanner.services;

import com.smartplanner.models.Notification;
import com.smartplanner.utils.SimpleLogger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing notifications
 */
public class NotificationService {
    private static final SimpleLogger logger = SimpleLogger.getLogger(NotificationService.class);

    private List<Notification> notifications;

    public NotificationService() {
        this.notifications = new ArrayList<>();
    }

    /**
     * Send a notification to a user
     */
    public void envoyerNotification(String utilisateurId, String titre, String message, String typeMessage) {
        Notification notification = new Notification(utilisateurId, titre, message, typeMessage);
        notifications.add(notification);
        logger.info("Notification sent to user {}: {}", utilisateurId, titre);
    }

    /**
     * Send delay notification
     */
    public void envoyerNotificationRetard(String utilisateurId, String description, int minutesRetard) {
        String titre = "Retard détecté";
        String message = "Un retard de " + minutesRetard + " minutes a été détecté: " + description;
        envoyerNotification(utilisateurId, titre, message, "RETARD_TRANSPORT");
    }

    /**
     * Send schedule change notification
     */
    public void envoyerNotificationChangement(String utilisateurId, String changement) {
        envoyerNotification(utilisateurId, "Changement de programme", changement, "CHANGEMENT_EMPLOIS");
    }

    /**
     * Send task reminder
     */
    public void envoyerRappelTache(String utilisateurId, String nomTache, int minutesAvant) {
        String titre = "Rappel: " + nomTache;
        String message = "La tâche " + nomTache + " commence dans " + minutesAvant + " minutes";
        envoyerNotification(utilisateurId, titre, message, "RAPPEL_TACHE");
    }

    /**
     * Mark notification as read
     */
    public void marquerCommeLue(String notificationId) {
        Notification notification = notifications.stream()
                .filter(n -> n.getId().equals(notificationId))
                .findFirst()
                .orElse(null);

        if (notification != null) {
            notification.setEstLue(true);
            logger.debug("Notification {} marked as read", notificationId);
        }
    }

    /**
     * Get unread notifications for a user
     */
    public List<Notification> getNotificationsNonLues(String utilisateurId) {
        return notifications.stream()
                .filter(n -> n.getUtilisateurId().equals(utilisateurId) && !n.isEstLue())
                .collect(Collectors.toList());
    }

    /**
     * Get all notifications for a user
     */
    public List<Notification> getNotifications(String utilisateurId) {
        return notifications.stream()
                .filter(n -> n.getUtilisateurId().equals(utilisateurId))
                .sorted((n1, n2) -> n2.getDateHeure().compareTo(n1.getDateHeure()))
                .collect(Collectors.toList());
    }

    /**
     * Get notifications by type
     */
    public List<Notification> getNotificationsByType(String utilisateurId, String typeMessage) {
        return notifications.stream()
                .filter(n -> n.getUtilisateurId().equals(utilisateurId) &&
                        n.getTypeMessage().equals(typeMessage))
                .collect(Collectors.toList());
    }

    /**
     * Delete a notification
     */
    public void supprimerNotification(String notificationId) {
        notifications.removeIf(n -> n.getId().equals(notificationId));
        logger.debug("Notification {} deleted", notificationId);
    }

    /**
     * Clear all notifications for a user
     */
    public void effacerNotifications(String utilisateurId) {
        notifications.removeIf(n -> n.getUtilisateurId().equals(utilisateurId));
        logger.info("All notifications for user {} cleared", utilisateurId);
    }

    public List<Notification> getAllNotifications() {
        return new ArrayList<>(notifications);
    }
}
