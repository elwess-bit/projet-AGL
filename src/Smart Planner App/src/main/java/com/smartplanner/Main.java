package com.smartplanner;

import com.smartplanner.enums.PriorityLevel;
import com.smartplanner.enums.TaskType;
import com.smartplanner.enums.TimeSlotType;
import com.smartplanner.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for the Smart Daily Planner application
 */
public class Main {
    private static SmartPlannerApplication app;
    private static Scanner scanner;
    private static String currentUserId;

    public static void main(String[] args) {
        app = new SmartPlannerApplication();
        scanner = new Scanner(System.in);

        System.out.println("\n============================================================================");
        System.out.println("                        SMART DAILY PLANNER                           ");
        System.out.println("              Intelligent Scheduling for Tunisian Students            ");
        System.out.println("============================================================================\n");

        boolean running = true;
        while (running) {
            if (currentUserId == null) {
                displayMainMenu();
            } else {
                displayUserMenu();
            }

            String choice = scanner.nextLine().trim();

            if (currentUserId == null) {
                running = handleMainMenuChoice(choice);
            } else {
                running = handleUserMenuChoice(choice);
            }
        }

        System.out.println("\n[OK] Thank you for using Smart Daily Planner. Goodbye!\n");
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Register New User");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter choice: ");
    }

    private static void displayUserMenu() {
        System.out.println("\n--- USER MENU ---");
        System.out.println("1. View Dashboard");
        System.out.println("2. Add Task");
        System.out.println("3. Add Availability");
        System.out.println("4. Add Constraint");
        System.out.println("5. Generate Schedule");
        System.out.println("6. View Planning");
        System.out.println("7. View Notifications");
        System.out.println("8. Manage Preferences");
        System.out.println("9. Signal Transportation Delay");
        System.out.println("10. Test TableauBord & Statistiques (Membre 5)");
        System.out.println("0. Logout");
        System.out.print("Enter choice: ");
    }

    private static boolean handleMainMenuChoice(String choice) {
        switch (choice) {
            case "1":
                registerUser();
                break;
            case "2":
                loginUser();
                break;
            case "3":
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true;
    }

    private static boolean handleUserMenuChoice(String choice) {
        switch (choice) {
            case "1":
                displayUserDashboard();
                break;
            case "2":
                addTask();
                break;
            case "3":
                addAvailability();
                break;
            case "4":
                addConstraint();
                break;
            case "5":
                generateSchedule();
                break;
            case "6":
                viewPlanning();
                break;
            case "7":
                viewNotifications();
                break;
            case "8":
                managePreferences();
                break;
            case "9":
                signalTransportationDelay();
                break;
            case "10":
                testTableauBordAndStatistiques();
                break;
            case "0":
                currentUserId = null;
                System.out.println("[OK] Logged out successfully");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true;
    }

    private static void registerUser() {
        System.out.println("\n--- REGISTER NEW USER ---");
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Study Level (e.g., L1, L2, L3, M1, M2): ");
        String level = scanner.nextLine().trim();

        Utilisateur user = app.registerUser(name, email, password, level);
        if (user == null) {
            System.out.println("[ERROR] Registration failed. Please verify your email, password, and try again.");
            return;
        }

        System.out.println("[OK] User registered successfully!");
        System.out.println("  Utilisateur créé : " + user.getNom() + " (" + user.getEmail() + ")");
    }

    private static void loginUser() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        Utilisateur user = app.loginUser(email, password);
        if (user != null) {
            currentUserId = user.getId();
            System.out.println("[OK] Connexion réussie !");
            System.out.println("  Bienvenue " + user.getNom());
        } else {
            System.out.println("[ERROR] Invalid login credentials. Please try again or register.");
        }
    }

    private static void displayUserDashboard() {
        if (currentUserId != null) {
            app.displayUserDashboard(currentUserId);
        }
    }

    private static void addTask() {
        System.out.println("\n--- ADD NEW TASK ---");
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.print("Duration (minutes): ");
        int duration;
        try {
            duration = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid duration. Please enter a positive number.");
            return;
        }

        System.out.println("Priority: 1=BASSE, 2=MOYENNE, 3=HAUTE");
        System.out.print("Select priority: ");
        int priorityChoice;
        try {
            priorityChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            priorityChoice = 2;
        }
        PriorityLevel priority = PriorityLevel.MOYENNE;
        if (priorityChoice >= 1 && priorityChoice <= PriorityLevel.values().length) {
            priority = PriorityLevel.values()[priorityChoice - 1];
        }

        System.out.println("Task Type: 1=COURS, 2=REVISION, 3=PERSONNELLE");
        System.out.print("Select type: ");
        int typeChoice;
        try {
            typeChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            typeChoice = 3;
        }
        TaskType type = TaskType.PERSONNELLE;
        if (typeChoice >= 1 && typeChoice <= TaskType.values().length) {
            type = TaskType.values()[typeChoice - 1];
        }

        Tache task = app.addTask(currentUserId, description, duration, priority, type);
        if (task == null) {
            System.out.println("[ERROR] Task cannot be created. Please check the information and try again.");
            return;
        }

        System.out.println("[OK] Task added successfully!");
        System.out.println("  Tâche ajoutée : " + task.getDescription() + " (" + task.getDureeMinutes() + "min)");
    }

    private static void addAvailability() {
        System.out.println("\n--- ADD AVAILABILITY ---");
        System.out.print("Day (e.g., MONDAY, TUESDAY): ");
        String day = scanner.nextLine().trim().toUpperCase();

        try {
            System.out.print("Start time (HH:mm): ");
            String startStr = scanner.nextLine().trim();
            LocalTime startTime = LocalTime.parse(startStr);

            System.out.print("End time (HH:mm): ");
            String endStr = scanner.nextLine().trim();
            LocalTime endTime = LocalTime.parse(endStr);

            Disponibilite availability = app.addAvailability(currentUserId, startTime, endTime, day);
            if (availability == null) {
                System.out.println("[ERROR] Could not add availability. Check for overlap or invalid time range.");
                return;
            }
            System.out.println("[OK] Availability added successfully!");
            System.out.println("  Créneau disponible : " + day + " " + startTime + "-" + endTime);
        } catch (Exception e) {
            System.out.println("[ERROR] Invalid time format. Use HH:mm.");
        }
    }

    private static void addConstraint() {
        System.out.println("\n--- ADD CONSTRAINT ---");
        System.out.println("Type: 1=TRANSPORTATION_DELAY, 2=BREAK_BETWEEN_CLASSES, 3=SCHEDULE_CHANGE, 4=PERSONAL_ACTIVITY");
        System.out.print("Select type: ");
        int typeChoice = Integer.parseInt(scanner.nextLine().trim());
        TimeSlotType type = TimeSlotType.values()[typeChoice - 1];

        System.out.print("Date and time (yyyy-MM-dd HH:mm): ");
        String dateTimeStr = scanner.nextLine().trim();
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr.replace(" ", "T"));

        System.out.print("Duration (minutes): ");
        int duration = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        Contrainte constraint = app.addConstraint(currentUserId, type, dateTime, duration, description);
        if (constraint == null) {
            System.out.println("[ERROR] Could not add constraint. Please verify the details and try again.");
            return;
        }
        System.out.println("[OK] Constraint added successfully!");
    }

    private static void signalTransportationDelay() {
        System.out.println("\n--- SIGNAL TRANSPORTATION DELAY ---");
        System.out.print("Delay duration (minutes): ");
        int delayMinutes;
        try {
            delayMinutes = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Invalid delay. Please enter a positive number.");
            return;
        }

        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        app.handleTransportationDelay(currentUserId, delayMinutes, description);
        System.out.println("[OK] Delay noted and planning updated if possible.");
    }

    private static void generateSchedule() {
        System.out.println("\n--- GENERATE SCHEDULE ---");
        System.out.print("Start date (yyyy-MM-dd): ");
        String startStr = scanner.nextLine().trim();
        LocalDate startDate = LocalDate.parse(startStr);

        System.out.print("End date (yyyy-MM-dd): ");
        String endStr = scanner.nextLine().trim();
        LocalDate endDate = LocalDate.parse(endStr);

        Planning planning = app.generateSchedule(currentUserId, startDate, endDate);
        if (planning != null) {
            System.out.println("[OK] Schedule generated successfully!");
            System.out.println("  Planning généré avec " + planning.getElements().size() + " tâches");
        } else {
            System.out.println("[ERROR] Could not generate schedule. Please add tasks and availability first.");
        }
    }

    private static void viewPlanning() {
        System.out.println("\n--- VIEW PLANNING ---");
        Planning planning = app.getUserPlanning(currentUserId);
        if (planning == null) {
            System.out.println("No planning found. Generate a schedule first.");
            return;
        }

        displayPlanningAsTable(planning);
    }

    private static void displayPlanningAsTable(Planning planning) {
        System.out.println("Planning ID: " + planning.getId());
        System.out.println("Period: " + planning.getDateDebut() + " to " + planning.getDateFin());
        System.out.println("\n+----------------+----------+----------+------------------------------+----------+");
        System.out.println("| Date           | Start    | End      | Task                         | Status   |");
        System.out.println("+----------------+----------+----------+------------------------------+----------+");

        planning.getElements().stream()
                .sorted((e1, e2) -> e1.getHeureDebut().compareTo(e2.getHeureDebut()))
                .forEach(element -> {
                    String date = element.getHeureDebut().toLocalDate().toString();
                    String start = element.getHeureDebut().toLocalTime().toString();
                    String end = element.getHeureFin().toLocalTime().toString();
                    String task = element.getDescription();
                    if (task.length() > 30) {
                        task = task.substring(0, 27) + "...";
                    }
                    String status = element.getStatut();
                    System.out.printf("| %-14s | %-8s | %-8s | %-28s | %-8s |%n",
                            date, start, end, task, status);
                });

        System.out.println("+----------------+----------+----------+------------------------------+----------+");
    }

    private static void viewNotifications() {
        System.out.println("\n--- NOTIFICATIONS ---");
        List<Notification> notifications = app.getUnreadNotifications(currentUserId);

        if (notifications.isEmpty()) {
            System.out.println("No unread notifications.");
            return;
        }

        System.out.println("Unread Notifications: " + notifications.size());
        System.out.println("-".repeat(80));

        for (Notification notif : notifications) {
            System.out.println("• " + notif.getTitre());
            System.out.println("  Message: " + notif.getMessage());
            System.out.println("  Time: " + notif.getDateHeure());
            System.out.println();
        }
    }

    private static void managePreferences() {
        System.out.println("\n--- MANAGE PREFERENCES ---");
        Preference pref = app.getPreferenceService().obtenirPreference(currentUserId);

        System.out.println("Current Preferences:");
        System.out.println("1. Notifications: " + (pref.isNotificationsActivees() ? "Enabled" : "Disabled"));
        System.out.println("2. Reminder time: " + pref.getMinutesRappelAvant() + " minutes before");
        System.out.println("3. Theme: " + pref.getTheme());
        System.out.println("4. Language: " + pref.getLanguePrefere());
        System.out.println("5. Daily study goal: " + (pref.getTempsEtudeIdealParJour() / 60) + " hours");
        System.out.println("6. Morning energy profile: " + pref.getEnergieMatin());
        System.out.println("7. Afternoon energy profile: " + pref.getEnergieApresMidi());
        System.out.println("8. Evening energy profile: " + pref.getEnergieSoir());
        System.out.println("\n0. Back");

        System.out.print("Select option to modify: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                boolean enabled = pref.isNotificationsActivees();
                pref.setNotificationsActivees(!enabled);
                System.out.println("[OK] Notifications " + (!enabled ? "enabled" : "disabled"));
                break;
            case "2":
                System.out.print("Enter reminder time (minutes): ");
                int minutes = Integer.parseInt(scanner.nextLine().trim());
                pref.setMinutesRappelAvant(minutes);
                System.out.println("[OK] Reminder time updated");
                break;
            case "3":
                System.out.print("Enter theme (CLAIR/SOMBRE): ");
                String theme = scanner.nextLine().trim();
                pref.setTheme(theme);
                System.out.println("[OK] Theme updated");
                break;
            case "4":
                System.out.print("Enter language (FRANCAIS/ARABE/ANGLAIS): ");
                String lang = scanner.nextLine().trim();
                pref.setLanguePrefere(lang);
                System.out.println("[OK] Language updated");
                break;
            case "5":
                System.out.print("Enter daily study goal (hours): ");
                int hours = Integer.parseInt(scanner.nextLine().trim());
                pref.setTempsEtudeIdealParJour(hours * 60);
                System.out.println("[OK] Study goal updated");
                break;
            case "6":
                System.out.print("Morning energy (ELEVÉ/MOYEN/FAIBLE): ");
                String morningEnergy = scanner.nextLine().trim().toUpperCase();
                pref.setEnergieMatin(morningEnergy);
                System.out.println("[OK] Morning energy profile updated");
                break;
            case "7":
                System.out.print("Afternoon energy (ELEVÉ/MOYEN/FAIBLE): ");
                String afternoonEnergy = scanner.nextLine().trim().toUpperCase();
                pref.setEnergieApresMidi(afternoonEnergy);
                System.out.println("[OK] Afternoon energy profile updated");
                break;
            case "8":
                System.out.print("Evening energy (ELEVÉ/MOYEN/FAIBLE): ");
                String eveningEnergy = scanner.nextLine().trim().toUpperCase();
                pref.setEnergieSoir(eveningEnergy);
                System.out.println("[OK] Evening energy profile updated");
                break;
            default:
                System.out.println("Returning to user menu.");
        }

        app.getPreferenceService().sauvegarderPreference(pref);
    }

    /**
     * Test la fonctionnalité TableauBord et Statistiques (Membre 5)
     */
    private static void testTableauBordAndStatistiques() {
        System.out.println("\n=================================================================");
        System.out.println("           TEST TABLEAUBORD & STATISTIQUES (MEMBRE 5)");
        System.out.println("=================================================================\n");

        // Creer un planning pour le test
        Planning planning = app.getUserPlanning(currentUserId);
        if (planning == null) {
            System.out.println("[INFO] Creation d'un planning pour la demonstration...");
            planning = new Planning(currentUserId, LocalDate.now(), LocalDate.now().plusDays(7));
        }

        // Test 1 : Creer une instance de TableauBord
        System.out.println("\n--- Test 1 : Instanciation de TableauBord ---");
        TableauBord tableauBord = new TableauBord(planning);
        System.out.println("[OK] TableauBord cree avec succes");

        // Test 2 : Afficher la vue du planning
        System.out.println("\n--- Test 2 : Affichage de la vue Planning ---");
        tableauBord.afficherPlanningVue();

        // Test 3 : Marquer des taches comme faites
        System.out.println("\n--- Test 3 : Marquer des taches comme completes ---");
        tableauBord.marquerTacheFaite(1);
        tableauBord.marquerTacheFaite(2);
        tableauBord.marquerTacheFaite(5);

        // Test 4 : Creer des statistiques
        System.out.println("\n--- Test 4 : Creation et affichage des Statistiques ---");
        Statistique stat1 = new Statistique(10, 7, "2026-04-20");
        stat1.afficherStatistiques();

        System.out.println();
        Statistique stat2 = new Statistique(15, 12, "2026-04-21");
        stat2.afficherStatistiques();

        // Test 5 : Test constructeur vide de Statistique
        System.out.println("\n--- Test 5 : Statistique avec constructeur vide ---");
        Statistique stat3 = new Statistique();
        stat3.setNbTachesTotal(20);
        stat3.setNbTachesCompletees(15);
        stat3.setDate("2026-04-22");
        stat3.calculerTauxCompletion();
        stat3.afficherStatistiques();

        // Test 6 : Ajouter des statistiques a l'historique du tableau de bord
        System.out.println("\n--- Test 6 : Ajouter des statistiques a l'historique ---");
        tableauBord.ajouterStatistique(stat1);
        tableauBord.ajouterStatistique(stat2);
        tableauBord.ajouterStatistique(stat3);
        System.out.println("[OK] " + tableauBord.getHistoriqueStats().size() + " statistiques ajoutees a l'historique");

        // Test 7 : Afficher l'historique
        System.out.println("\n--- Test 7 : Contenu de l'historique ---");
        System.out.println("Nombre de statistiques en historique : " + tableauBord.getHistoriqueStats().size());
        for (int i = 0; i < tableauBord.getHistoriqueStats().size(); i++) {
            Statistique stat = tableauBord.getHistoriqueStats().get(i);
            System.out.println("  [" + (i+1) + "] Date: " + stat.getDate() + " | Taux: " + 
                    String.format("%.2f", stat.getTauxCompletion()) + "% | Completees: " + 
                    stat.getNbTachesCompletees() + "/" + stat.getNbTachesTotal());
        }

        // Test 8 : Test des getters et setters
        System.out.println("\n--- Test 8 : Getters et Setters ---");
        System.out.println("Planning actuel : " + (tableauBord.getPlanningActuel() != null ? 
                tableauBord.getPlanningActuel().getId() : "null"));
        System.out.println("Nombre de statistiques : " + tableauBord.getHistoriqueStats().size());

        System.out.println("\n=================================================================");
        System.out.println("           [OK] TOUS LES TESTS COMPLETES AVEC SUCCES");
        System.out.println("=================================================================\n");
    }
}