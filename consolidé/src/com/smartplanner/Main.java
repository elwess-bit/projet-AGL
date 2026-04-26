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
    // Codes de couleur ANSI pour terminal PRO
    private static final String RESET = "\u001B[0m";
    private static final String ROUGE = "\u001B[31m";
    private static final String VERT = "\u001B[32m";
    private static final String JAUNE = "\u001B[33m";
    private static final String BLEU = "\u001B[34m";
    private static final String VIOLET = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String BLANC = "\u001B[37m";
    private static final String FOND_VERT = "\u001B[42m";
    private static final String FOND_ROUGE = "\u001B[41m";
    private static final String FOND_JAUNE = "\u001B[43m";
    private static final String GRAS = "\u001B[1m";
    private static final String SOULIGNE = "\u001B[4m";

    private static SmartPlannerApplication app;
    private static Scanner scanner;
    private static String currentUserId;

    public static void main(String[] args) {
        app = new SmartPlannerApplication();
        scanner = new Scanner(System.in);

        System.out.println("\n============================================================================");
        System.out.println("                        SMART DAILY PLANNER                           ");
        System.out.println("              Planificateur Intelligent pour Étudiants Tunisiens            ");
        System.out.println("============================================================================\n");

        boolean running = true;
        while (running) {
            if (currentUserId == null) {
                afficherMenuPrincipal();
            } else {
                afficherMenuUtilisateur();
            }

            String choice = scanner.nextLine().trim();

            if (currentUserId == null) {
                running = traiterChoixPrincipal(choice);
            } else {
                traiterChoixUtilisateur(choice);
            }
        }

        System.out.println("\n[OK] Merci d'avoir utilisé Smart Daily Planner. Au revoir !\n");
        scanner.close();
    }

    private static void afficherMenuPrincipal() {
        System.out.println("\n" + CYAN + "========================" + RESET);
        System.out.println(CYAN + "    🧭 MENU PRINCIPAL" + RESET);
        System.out.println(CYAN + "========================" + RESET);
        System.out.println(BLANC + "1." + RESET + " " + VERT + "Inscrire un nouvel utilisateur" + RESET);
        System.out.println(BLANC + "2." + RESET + " " + JAUNE + "Se connecter" + RESET);
        System.out.println(BLANC + "3." + RESET + " " + ROUGE + "Quitter" + RESET);
        System.out.print(BLEU + "Choix : " + RESET);
    }

    private static boolean traiterChoixPrincipal(String choix) {
        switch (choix) {
            case "1":
                registerUser();
                break;
            case "2":
                loginUser();
                break;
            case "3":
                return false;
            default:
                System.out.println("[ERREUR] Choix invalide. Veuillez réessayer.");
        }
        return true;
    }

    private static void afficherMenuUtilisateur() {
        System.out.println("\n--- MENU UTILISATEUR ---");
        System.out.println("1. 📊 TABLEAU DE BORD COMPLET (statistiques + planning + notifications)");
        System.out.println("2. ➕ Ajouter une tâche");
        System.out.println("3. 📅 Ajouter une disponibilité");
        System.out.println("4. ⚠️ Ajouter une contrainte");
        System.out.println("5. 🚀 Générer le planning");
        System.out.println("6. 👁️ Voir le planning");
        System.out.println("7. 🔔 Voir les notifications");
        System.out.println("8. ⚙️ Gérer les préférences");
        System.out.println("9. 🚗 Signaler un retard de transport");
        System.out.println("0. 🔌 Se déconnecter");
        System.out.print("Choix : ");
    }

    private static void traiterChoixUtilisateur(String choix) {
        switch (choix) {
            case "1":
                afficherTableauBordComplet();
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
                signalerRetardTransport();
                break;
            case "0":
                currentUserId = null;
                System.out.println("[OK] Déconnexion réussie");
                break;
            default:
                System.out.println("[ERREUR] Choix invalide. Veuillez réessayer.");
        }
    }

    private static void registerUser() {
        System.out.println("\n--- INSCRIPTION D'UN NOUVEL UTILISATEUR ---");
        System.out.print("Nom : ");
        String name = scanner.nextLine().trim();

        System.out.print("Email : ");
        String email = scanner.nextLine().trim();

        System.out.print("Mot de passe : ");
        String password = scanner.nextLine().trim();

        System.out.print("Niveau d'étude (ex : L1, L2, L3, M1, M2) : ");
        String level = scanner.nextLine().trim();

        Utilisateur user = app.registerUser(name, email, password, level);
        if (user == null) {
            System.out.println("[ERREUR] L'inscription a échoué. Vérifiez votre email et mot de passe, puis réessayez.");
            return;
        }

        System.out.println("[OK] Utilisateur inscrit avec succès !");
        System.out.println("  Utilisateur créé : " + user.getNom() + " (" + user.getEmail() + ")");
    }

    private static void loginUser() {
        System.out.println("\n--- CONNEXION ---");
        System.out.print("Email : ");
        String email = scanner.nextLine().trim();

        System.out.print("Mot de passe : ");
        String password = scanner.nextLine().trim();

        Utilisateur user = app.loginUser(email, password);
        if (user != null) {
            currentUserId = user.getId();
            System.out.println("[OK] Connexion réussie !");
            System.out.println("  Bienvenue " + user.getNom());
        } else {
            System.out.println("[ERREUR] Identifiants invalides. Veuillez réessayer ou vous inscrire.");
        }
    }

    private static void displayUserDashboard() {
        if (currentUserId != null) {
            app.displayUserDashboard(currentUserId);
        }
    }

    private static void addTask() {
        System.out.println("\n--- AJOUTER UNE TÂCHE ---");
        System.out.print("Description : ");
        String description = scanner.nextLine().trim();

        System.out.print("Durée (minutes) : ");
        int duration;
        try {
            duration = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERREUR] Durée invalide. Veuillez entrer un nombre positif.");
            return;
        }

        System.out.println("Priorité : 1=BASSE, 2=MOYENNE, 3=HAUTE");
        System.out.print("Choix de priorité : ");
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

        System.out.println("Type de tâche : 1=COURS, 2=REVISION, 3=PERSONNELLE");
        System.out.print("Choix du type : ");
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
            System.out.println("[ERREUR] Impossible de créer la tâche. Vérifiez les informations et réessayez.");
            return;
        }

        System.out.println("[OK] Tâche ajoutée avec succès !");
        System.out.println("  Tâche ajoutée : " + task.getDescription() + " (" + task.getDureeMinutes() + " min)");

        Planning currentPlanning = app.getUserPlanning(currentUserId);
        if (currentPlanning != null) {
            System.out.println("[INFO] Mise à jour du planning existant avec la nouvelle tâche...");
            Planning updatedPlanning = app.generateSchedule(currentUserId, currentPlanning.getDateDebut(), currentPlanning.getDateFin());
            if (updatedPlanning != null) {
                System.out.println("[OK] Planning mis à jour.");
                displayPlanningAsTable(updatedPlanning);
            } else {
                System.out.println("[ATTENTION] Le planning n'a pas pu être régénéré automatiquement.");
            }
        }
    }

    private static void addAvailability() {
        System.out.println("\n--- AJOUTER UNE DISPONIBILITÉ ---");
        System.out.print("Jour (par exemple LUNDI, MARDI) : ");
        String day = scanner.nextLine().trim().toUpperCase();

        LocalTime startTime = promptTime("Heure de début (HH:mm) : ");
        LocalTime endTime;
        while (true) {
            endTime = promptTime("Heure de fin (HH:mm) : ");
            if (!endTime.isAfter(startTime)) {
                System.out.println("[ERREUR] L'heure de fin doit être après l'heure de début.");
            } else {
                break;
            }
        }

        Disponibilite availability = app.addAvailability(currentUserId, startTime, endTime, day);
        if (availability == null) {
            System.out.println("[ERREUR] Impossible d'ajouter la disponibilité. Vérifiez le chevauchement ou la plage horaire.");
            return;
        }
        System.out.println("[OK] Disponibilité ajoutée avec succès !");
        System.out.println("  Créneau disponible : " + day + " " + startTime + " - " + endTime);
    }

    private static void addConstraint() {
        System.out.println("\n--- AJOUTER UNE CONTRAINTE ---");
        System.out.println("Type : 1=RETARD_TRANSPORT, 2=PAUSE_ENTRE_COURS, 3=CHANGEMENT_PLANNING, 4=ACTIVITE_PERSONNELLE");
        System.out.print("Choix du type : ");
        int typeChoice = Integer.parseInt(scanner.nextLine().trim());
        TimeSlotType type = TimeSlotType.values()[typeChoice - 1];

        System.out.print("Date et heure (yyyy-MM-dd HH:mm) : ");
        String dateTimeStr = scanner.nextLine().trim();
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr.replace(" ", "T"));

        System.out.print("Durée (minutes) : ");
        int duration = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Description : ");
        String description = scanner.nextLine().trim();

        Contrainte constraint = app.addConstraint(currentUserId, type, dateTime, duration, description);
        if (constraint == null) {
            System.out.println("[ERREUR] Impossible d'ajouter la contrainte. Vérifiez les détails et réessayez.");
            return;
        }
        System.out.println("[OK] Contrainte ajoutée avec succès !");
    }

    private static void signalerRetardTransport() {
        System.out.println("\n--- SIGNALER UN RETARD DE TRANSPORT ---");
        System.out.print("Durée du retard (minutes) : ");
        int delayMinutes;
        try {
            delayMinutes = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERREUR] Retard invalide. Veuillez entrer un nombre positif.");
            return;
        }

        System.out.print("Description : ");
        String description = scanner.nextLine().trim();

        app.handleTransportationDelay(currentUserId, delayMinutes, description);
        System.out.println("[OK] Retard noté et planning mis à jour si possible.");
    }

    private static void generateSchedule() {
        System.out.println("\n--- GÉNÉRER LE PLANNING ---");
        LocalDate startDate = promptDate("Date de début (yyyy-MM-dd) : ");

        LocalDate endDate;
        while (true) {
            endDate = promptDate("Date de fin (yyyy-MM-dd) : ");
            if (endDate.isBefore(startDate)) {
                System.out.println("[ERREUR] La date de fin doit être le même jour ou après la date de début.");
            } else {
                break;
            }
        }

        Planning planning = app.generateSchedule(currentUserId, startDate, endDate);
        if (planning != null) {
            System.out.println("[OK] Planning généré avec succès !");
            System.out.println("  Planning généré avec " + planning.getElements().size() + " tâches");
        } else {
            System.out.println("[ERREUR] Impossible de générer le planning. Ajoutez des tâches et des disponibilités d'abord.");
        }
    }

    private static void viewPlanning() {
        System.out.println("\n--- VOIR LE PLANNING ---");
        Planning planning = app.getUserPlanning(currentUserId);
        if (planning == null) {
            System.out.println("Aucun planning trouvé. Générez un planning d'abord.");
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
            System.out.println("Aucune notification non lue.");
            return;
        }

        System.out.println("Notifications non lues : " + notifications.size());
        System.out.println("-".repeat(80));

        for (Notification notif : notifications) {
            System.out.println("• " + notif.getTitre());
            System.out.println("  Message : " + notif.getMessage());
            System.out.println("  Heure : " + notif.getDateHeure());
            System.out.println();
        }
    }

    private static void managePreferences() {
        System.out.println("\n--- GÉRER LES PRÉFÉRENCES ---");
        Preference pref = app.getPreferenceService().obtenirPreference(currentUserId);

        System.out.println("Préférences actuelles :");
        System.out.println("1. Notifications : " + (pref.isNotificationsActivees() ? "Activées" : "Désactivées"));
        System.out.println("2. Temps de rappel : " + pref.getMinutesRappelAvant() + " minutes avant");
        System.out.println("3. Thème : " + pref.getTheme());
        System.out.println("4. Langue : " + pref.getLanguePrefere());
        System.out.println("5. Objectif d'étude journalier : " + (pref.getTempsEtudeIdealParJour() / 60) + " heures");
        System.out.println("6. Profil d'énergie matin : " + pref.getEnergieMatin());
        System.out.println("7. Profil d'énergie après-midi : " + pref.getEnergieApresMidi());
        System.out.println("8. Profil d'énergie soir : " + pref.getEnergieSoir());
        System.out.println("\n0. Retour");

        System.out.print("Choix à modifier : ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                boolean enabled = pref.isNotificationsActivees();
                pref.setNotificationsActivees(!enabled);
                System.out.println("[OK] Notifications " + (!enabled ? "activées" : "désactivées"));
                break;
            case "2":
                System.out.print("Temps de rappel (minutes) : ");
                int minutes = Integer.parseInt(scanner.nextLine().trim());
                pref.setMinutesRappelAvant(minutes);
                System.out.println("[OK] Temps de rappel mis à jour");
                break;
            case "3":
                System.out.print("Thème (CLAIR/SOMBRE) : ");
                String theme = scanner.nextLine().trim();
                pref.setTheme(theme);
                System.out.println("[OK] Thème mis à jour");
                break;
            case "4":
                System.out.print("Langue (FRANCAIS/ARABE/ANGLAIS) : ");
                String lang = scanner.nextLine().trim();
                pref.setLanguePrefere(lang);
                System.out.println("[OK] Langue mise à jour");
                break;
            case "5":
                System.out.print("Objectif d'étude journalier (heures) : ");
                int hours = Integer.parseInt(scanner.nextLine().trim());
                pref.setTempsEtudeIdealParJour(hours * 60);
                System.out.println("[OK] Objectif d'étude mis à jour");
                break;
            case "6":
                System.out.print("Énergie matin (ELEVÉ/MOYEN/FAIBLE) : ");
                String morningEnergy = scanner.nextLine().trim().toUpperCase();
                pref.setEnergieMatin(morningEnergy);
                System.out.println("[OK] Profil d'énergie matin mis à jour");
                break;
            case "7":
                System.out.print("Énergie après-midi (ELEVÉ/MOYEN/FAIBLE) : ");
                String afternoonEnergy = scanner.nextLine().trim().toUpperCase();
                pref.setEnergieApresMidi(afternoonEnergy);
                System.out.println("[OK] Profil d'énergie après-midi mis à jour");
                break;
            case "8":
                System.out.print("Énergie soir (ELEVÉ/MOYEN/FAIBLE) : ");
                String eveningEnergy = scanner.nextLine().trim().toUpperCase();
                pref.setEnergieSoir(eveningEnergy);
                System.out.println("[OK] Profil d'énergie soir mis à jour");
                break;
            default:
                System.out.println("Retour au menu utilisateur.");
        }

        app.getPreferenceService().sauvegarderPreference(pref);
    }

    private static void afficherTableauBordComplet() {
        System.out.println("\n=== TABLEAU DE BORD ET STATISTIQUES ===");

        // Afficher le tableau de bord principal
        app.displayUserDashboard(currentUserId);

        // Afficher les statistiques d'utilisation
        List<Tache> toutesTaches = app.getTaskService().obtenirTachesUtilisateur(currentUserId);
        int total = toutesTaches.size();
        int terminees = (int) toutesTaches.stream().filter(Tache::isCompletee).count();
        int enAttente = total - terminees;
        double tauxCompletion = total > 0 ? (double) terminees / total * 100 : 0.0;

        System.out.println("\n--- STATISTIQUES DE TÂCHES ---");
        System.out.println("Tâches totales : " + total);
        System.out.println("Tâches terminées : " + terminees);
        System.out.println("Tâches en attente : " + enAttente);
        System.out.printf("Taux de complétion : %.2f%%\n", tauxCompletion);

        Planning planning = app.getUserPlanning(currentUserId);
        System.out.println("\n--- RÉCAPITULATIF DU PLANNING ---");
        if (planning != null) {
            System.out.println("Planning actuel : " + planning.getId());
            System.out.println("Période : " + planning.getDateDebut() + " à " + planning.getDateFin());
            System.out.println("Tâches planifiées : " + planning.getElements().size());
        } else {
            System.out.println("Aucun planning disponible pour le moment.");
        }
    }
    private static LocalDate promptDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (Exception e) {
                System.out.println("[ERREUR] Format de date invalide. Utilisez yyyy-MM-dd.");
            }
        }
    }

    private static LocalTime promptTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalTime.parse(input);
            } catch (Exception e) {
                System.out.println("[ERREUR] Format horaire invalide. Utilisez HH:mm.");
            }
        }
    }}