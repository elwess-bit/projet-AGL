package com.smartplanner;

import com.smartplanner.enums.PriorityLevel;
import com.smartplanner.enums.TaskType;
import com.smartplanner.enums.TimeSlotType;
import com.smartplanner.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Demonstration class to show the application output
 */
public class SmartPlannerDemo {
    
    public static void main(String[] args) {
        System.out.println("\n=== Smart Daily Planner – Sprint 1 ===\n");
        
        SmartPlannerApplication app = new SmartPlannerApplication();
        
        // --- Authentification (Ahmed) ---
        System.out.println("--- Authentification (Ahmed) ---");
        Utilisateur ahmed = app.registerUser("Ahmed Benali", "ahmed@mail.com", "password123", "L3");
        if (ahmed != null) {
            System.out.println("  Utilisateur créé : Ahmed Benali (ahmed@mail.com)");
        }
        
        Utilisateur loggedIn = app.loginUser("ahmed@mail.com", "password123");
        if (loggedIn != null) {
            System.out.println("  Connexion réussie !");
        }
        System.out.println();
        
        String userId = ahmed.getId();
        
        // --- Ajout de tâches ---
        System.out.println("--- Ajout de tâches ---");
        Tache task1 = app.addTask(userId, "Révision Algorithmique", 120, PriorityLevel.HAUTE, TaskType.REVISION);
        System.out.println("  Tâche ajoutée : Révision Algorithmique (120min)");
        
        Tache task2 = app.addTask(userId, "Préparation TP Java", 90, PriorityLevel.MOYENNE, TaskType.COURS);
        System.out.println("  Tâche ajoutée : Préparation TP Java (90min)");
        
        Tache task3 = app.addTask(userId, "Achat fournitures", 30, PriorityLevel.BASSE, TaskType.PERSONNELLE);
        System.out.println("  Tâche ajoutée : Achat fournitures (30min)");
        System.out.println();
        
        // --- Ajout de disponibilités ---
        System.out.println("--- Disponibilités ---");
        Disponibilite disp1 = app.addAvailability(userId, LocalTime.of(8, 0), LocalTime.of(12, 0), "MONDAY");
        System.out.println("  Créneau disponible : MONDAY 08:00-12:00");
        
        Disponibilite disp2 = app.addAvailability(userId, LocalTime.of(14, 0), LocalTime.of(18, 0), "MONDAY");
        System.out.println("  Créneau disponible : MONDAY 14:00-18:00");
        
        Disponibilite disp3 = app.addAvailability(userId, LocalTime.of(9, 0), LocalTime.of(12, 0), "TUESDAY");
        System.out.println("  Créneau disponible : TUESDAY 09:00-12:00");
        System.out.println();
        
        // --- Génération du planning ---
        System.out.println("--- Génération du planning ---");
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(2);
        
        Planning planning = app.generateSchedule(userId, startDate, endDate);
        if (planning != null) {
            System.out.println("  Planning généré avec " + planning.getElements().size() + " tâches");
        }
        System.out.println();
        
        // --- Affichage du planning ---
        System.out.println("--- Planning détaillé ---");
        displayPlanning(planning);
        System.out.println();
        
        // --- Dashboard ---
        System.out.println("--- Dashboard Utilisateur ---");
        app.displayUserDashboard(userId);
        
        // --- Notifications ---
        System.out.println("--- Notifications ---");
        app.getNotificationService().envoyerNotification(userId, "Rappel", "Votre tâche 'Révision' commence dans 15 min", "RAPPEL");
        System.out.println("  Notification envoyée : Rappel pour Révision");
        System.out.println();
        
        System.out.println("=== Fin de la démonstration ===");
    }
    
    private static void displayPlanning(Planning planning) {
        if (planning == null || planning.getElements().isEmpty()) {
            System.out.println("  Aucun planning généré.");
            return;
        }
        
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        for (ElementPlanning elem : planning.getElements()) {
            String time = elem.getHeureDebut().format(timeFormatter) + " - " + 
                         elem.getHeureFin().format(timeFormatter);
            System.out.println("  • " + elem.getDescription() + " : " + time);
        }
    }
}