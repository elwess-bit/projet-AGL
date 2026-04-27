package com.smartplanner;

import com.smartplanner.enums.PriorityLevel;
import com.smartplanner.enums.TaskType;
import com.smartplanner.enums.TimeSlotType;
import com.smartplanner.models.*;
import com.smartplanner.services.QRCodeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for the Smart Daily Planner application
 */
public class Main {
    // ANSI color codes for terminal
    private static final String RESET = "\u001B[0m";
    private static final String ROUGE = "\u001B[31m";
    private static final String VERT = "\u001B[32m";
    private static final String JAUNE = "\u001B[33m";
    private static final String BLEU = "\u001B[34m";
    private static final String VIOLET = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String BLANC = "\u001B[37m";
    private static final String GRAS = "\u001B[1m";

    private static SmartPlannerApplication app;
    private static Scanner scanner;
    private static String currentUserId;
    private static QRCodeService qrCodeService;

    public static void main(String[] args) {
        app = new SmartPlannerApplication();
        scanner = new Scanner(System.in);
        qrCodeService = new QRCodeService();

        System.out.println("\n" + CYAN + GRAS + "============================================================================" + RESET);
        System.out.println(CYAN + GRAS + "                        SMART DAILY PLANNER                           " + RESET);
        System.out.println(CYAN + "              Planificateur Intelligent pour Étudiants Tunisiens            " + RESET);
        System.out.println(CYAN + "============================================================================\n" + RESET);

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

        System.out.println("\n" + VERT + "[✓] Merci d'avoir utilisé Smart Daily Planner. Au revoir !\n" + RESET);
        scanner.close();
    }

    private static void afficherMenuPrincipal() {
        System.out.println("\n" + CYAN + "========================" + RESET);
        System.out.println(CYAN + GRAS + "    🧭 MENU PRINCIPAL" + RESET);
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
                System.out.println(ROUGE + "[✗] Choix invalide. Veuillez réessayer." + RESET);
        }
        return true;
    }

    private static void afficherMenuUtilisateur() {
        System.out.println("\n" + VIOLET + GRAS + "╔════════════════════════════════════════╗" + RESET);
        System.out.println(VIOLET + GRAS + "║        👤 MENU UTILISATEUR               ║" + RESET);
        System.out.println(VIOLET + GRAS + "╠════════════════════════════════════════╣" + RESET);
        System.out.println(VIOLET + "║ " + RESET + "1. " + CYAN + "📊 TABLEAU DE BORD COMPLET" + RESET + VIOLET + "             ║" + RESET);
        System.out.println(VIOLET + "║ " + RESET + "2. " + VERT + "➕ Ajouter une tâche" + RESET + VIOLET + "                        ║" + RESET);
        System.out.println(VIOLET + "║ " + RESET + "3. " + BLEU + "📅 Ajouter une disponibilité" + RESET + VIOLET + "                 ║" + RESET);
        System.out.println(VIOLET + "║ " + RESET + "4. " + JAUNE + "⚠️  Ajouter une contrainte" + RESET + VIOLET + "                    ║" + RESET);
        System.out.println(VIOLET + "║ " + RESET + "5. " + VIOLET + "🚀 Générer le planning" + RESET + VIOLET + "                       ║" + RESET);
        System.out.println(VIOLET + "║ " + RESET + "6. " + BLANC + "👁️  Voir le planning" + RESET + VIOLET + "                         ║" + RESET);
        System.out.println(VIOLET + "║ " + RESET + "7. " + JAUNE + "🔔 Voir les notifications" + RESET + VIOLET + "                    ║" + RESET);
        System.out.println(VIOLET + "║ " + RESET + "8. " + CYAN + "⚙️  Gérer les préférences" + RESET + VIOLET + "                    ║" + RESET);
        System.out.println(VIOLET + "║ " + RESET + "9. " + ROUGE + "🚗 Signaler un retard" + RESET + VIOLET + "                       ║" + RESET);
        System.out.println(VIOLET + "║ " + RESET + "10. " + CYAN + "📱 GÉNÉRER QR CODE DU PLANNING" + RESET + VIOLET + "       ║" + RESET);
        System.out.println(VIOLET + "║ " + RESET + "0. " + ROUGE + "🔌 Se déconnecter" + RESET + VIOLET + "                         ║" + RESET);
        System.out.println(VIOLET + "╚════════════════════════════════════════╝" + RESET);
        System.out.print(BLEU + "👉 Votre choix : " + RESET);
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
            case "10":
                genererQRCodePlanning();
                break;
            case "0":
                currentUserId = null;
                System.out.println(VERT + "[✓] Déconnexion réussie" + RESET);
                break;
            default:
                System.out.println(ROUGE + "[✗] Choix invalide. Veuillez réessayer." + RESET);
        }
        pressEnterToContinue();
    }

    private static void pressEnterToContinue() {
        System.out.print("\n" + BLANC + "Appuyez sur Entrée pour continuer..." + RESET);
        scanner.nextLine();
    }

    private static void registerUser() {
        System.out.println("\n" + CYAN + "--- INSCRIPTION D'UN NOUVEL UTILISATEUR ---" + RESET);
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
            System.out.println(ROUGE + "[✗] L'inscription a échoué." + RESET);
            return;
        }

        System.out.println(VERT + "[✓] Utilisateur inscrit avec succès !" + RESET);
        System.out.println("  Utilisateur créé : " + JAUNE + user.getNom() + RESET + " (" + user.getEmail() + ")");
    }

    private static void loginUser() {
        System.out.println("\n" + CYAN + "--- CONNEXION ---" + RESET);
        System.out.print("Email : ");
        String email = scanner.nextLine().trim();

        System.out.print("Mot de passe : ");
        String password = scanner.nextLine().trim();

        Utilisateur user = app.loginUser(email, password);
        if (user != null) {
            currentUserId = user.getId();
            System.out.println(VERT + "[✓] Connexion réussie !" + RESET);
            System.out.println("  Bienvenue " + JAUNE + user.getNom() + RESET);
        } else {
            System.out.println(ROUGE + "[✗] Identifiants invalides." + RESET);
        }
    }

    private static void addTask() {
        System.out.println("\n" + CYAN + "--- AJOUTER UNE TÂCHE ---" + RESET);
        System.out.print("Description : ");
        String description = scanner.nextLine().trim();

        System.out.print("Durée (minutes) : ");
        int duration;
        try {
            duration = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(ROUGE + "[✗] Durée invalide." + RESET);
            return;
        }

        System.out.println("Priorité : " + VERT + "1=BASSE" + RESET + ", " + JAUNE + "2=MOYENNE" + RESET + ", " + ROUGE + "3=HAUTE" + RESET);
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
            System.out.println(ROUGE + "[✗] Impossible de créer la tâche." + RESET);
            return;
        }

        System.out.println(VERT + "[✓] Tâche ajoutée avec succès !" + RESET);
        System.out.println("  Tâche ajoutée : " + JAUNE + task.getDescription() + RESET + " (" + task.getDureeMinutes() + " min)");
    }

    private static void addAvailability() {
        System.out.println("\n" + CYAN + "--- AJOUTER UNE DISPONIBILITÉ ---" + RESET);
        System.out.print("Jour (LUNDI, MARDI, ...) : ");
        String day = scanner.nextLine().trim().toUpperCase();

        LocalTime startTime = promptTime("Heure de début (HH:mm) : ");
        LocalTime endTime;
        while (true) {
            endTime = promptTime("Heure de fin (HH:mm) : ");
            if (!endTime.isAfter(startTime)) {
                System.out.println(ROUGE + "[✗] L'heure de fin doit être après l'heure de début." + RESET);
            } else {
                break;
            }
        }

        Disponibilite availability = app.addAvailability(currentUserId, startTime, endTime, day);
        if (availability == null) {
            System.out.println(ROUGE + "[✗] Impossible d'ajouter la disponibilité." + RESET);
            return;
        }
        System.out.println(VERT + "[✓] Disponibilité ajoutée avec succès !" + RESET);
        System.out.println("  Créneau : " + JAUNE + day + " " + startTime + " - " + endTime + RESET);
    }

    private static void addConstraint() {
        System.out.println("\n" + CYAN + "--- AJOUTER UNE CONTRAINTE ---" + RESET);
        System.out.println("Type : 1=RETARD_TRANSPORT, 2=PAUSE_ENTRE_COURS, 3=CHANGEMENT_PLANNING, 4=ACTIVITE_PERSONNELLE");
        System.out.print("Choix du type : ");
        int typeChoice;
        try {
            typeChoice = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(ROUGE + "[✗] Type invalide." + RESET);
            return;
        }
        TimeSlotType type = TimeSlotType.values()[typeChoice - 1];

        LocalDateTime dateTime = promptDateTime("Date et heure (yyyy-MM-dd HH:mm) : ");

        System.out.print("Durée (minutes) : ");
        int duration;
        try {
            duration = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(ROUGE + "[✗] Durée invalide." + RESET);
            return;
        }

        System.out.print("Description : ");
        String description = scanner.nextLine().trim();

        Contrainte constraint = app.addConstraint(currentUserId, type, dateTime, duration, description);
        if (constraint == null) {
            System.out.println(ROUGE + "[✗] Impossible d'ajouter la contrainte." + RESET);
            return;
        }
        System.out.println(VERT + "[✓] Contrainte ajoutée avec succès !" + RESET);
    }

    private static LocalDateTime promptDateTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDateTime.parse(input.replace(" ", "T"));
            } catch (DateTimeParseException e) {
                System.out.println(ROUGE + "[✗] Format invalide. Utilisez yyyy-MM-dd HH:mm" + RESET);
            }
        }
    }

    private static void signalerRetardTransport() {
        System.out.println("\n" + CYAN + "--- SIGNALER UN RETARD DE TRANSPORT ---" + RESET);
        System.out.print("Durée du retard (minutes) : ");
        int delayMinutes;
        try {
            delayMinutes = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(ROUGE + "[✗] Retard invalide." + RESET);
            return;
        }

        System.out.print("Description : ");
        String description = scanner.nextLine().trim();

        app.handleTransportationDelay(currentUserId, delayMinutes, description);
        System.out.println(VERT + "[✓] Retard noté." + RESET);
    }

    private static void generateSchedule() {
        System.out.println("\n" + CYAN + "--- GÉNÉRER LE PLANNING ---" + RESET);
        LocalDate startDate = promptDate("Date de début (yyyy-MM-dd) : ");
        LocalDate endDate = promptDate("Date de fin (yyyy-MM-dd) : ");

        if (endDate.isBefore(startDate)) {
            System.out.println(ROUGE + "[✗] La date de fin doit être après la date de début." + RESET);
            return;
        }

        Planning planning = app.generateSchedule(currentUserId, startDate, endDate);
        if (planning != null) {
            System.out.println(VERT + "[✓] Planning généré avec succès !" + RESET);
            System.out.println("  Planning avec " + JAUNE + planning.getElements().size() + RESET + " tâches");
        } else {
            System.out.println(ROUGE + "[✗] Impossible de générer le planning." + RESET);
        }
    }

    private static void viewPlanning() {
        System.out.println("\n" + CYAN + "--- VOIR LE PLANNING ---" + RESET);
        Planning planning = app.getUserPlanning(currentUserId);
        if (planning == null || planning.getElements().isEmpty()) {
            System.out.println(JAUNE + "[!] Aucun planning trouvé. Générez-en un d'abord." + RESET);
            return;
        }
        displayPlanningAsTable(planning);
    }

    private static void displayPlanningAsTable(Planning planning) {
        System.out.println("\n" + CYAN + "📅 PLANNING GÉNÉRÉ" + RESET);
        System.out.println("ID: " + planning.getId());
        System.out.println("Période: " + planning.getDateDebut() + " → " + planning.getDateFin());
        System.out.println("\n" + VIOLET + "+----------------+----------+----------+----------------------------------+----------+" + RESET);
        System.out.println(VIOLET + "| Date           | Début    | Fin      | Tâche                            | Statut   |" + RESET);
        System.out.println(VIOLET + "+----------------+----------+----------+----------------------------------+----------+" + RESET);

        planning.getElements().stream()
                .sorted((e1, e2) -> e1.getHeureDebut().compareTo(e2.getHeureDebut()))
                .forEach(element -> {
                    String date = element.getHeureDebut().toLocalDate().toString();
                    String start = element.getHeureDebut().toLocalTime().toString();
                    String end = element.getHeureFin().toLocalTime().toString();
                    String task = element.getDescription();
                    if (task.length() > 30) task = task.substring(0, 27) + "...";
                    String status = element.getStatut();
                    System.out.printf("| %-14s | %-8s | %-8s | %-30s | %-8s |%n",
                            date, start, end, task, status);
                });

        System.out.println(VIOLET + "+----------------+----------+----------+----------------------------------+----------+" + RESET);
    }

    private static void viewNotifications() {
        System.out.println("\n" + CYAN + "--- NOTIFICATIONS ---" + RESET);
        List<Notification> notifications = app.getUnreadNotifications(currentUserId);

        if (notifications.isEmpty()) {
            System.out.println(VERT + "[✓] Aucune notification non lue." + RESET);
            return;
        }

        System.out.println("Notifications non lues : " + JAUNE + notifications.size() + RESET);
        for (Notification notif : notifications) {
            System.out.println("• " + CYAN + notif.getTitre() + RESET);
            System.out.println("  Message : " + notif.getMessage());
            System.out.println("  Heure : " + notif.getDateHeure());
            System.out.println();
        }
    }

    private static void managePreferences() {
        System.out.println("\n" + CYAN + "--- GÉRER LES PRÉFÉRENCES ---" + RESET);
        Preference pref = app.getPreferenceService().obtenirPreference(currentUserId);

        System.out.println("Préférences actuelles :");
        System.out.println("1. Notifications : " + (pref.isNotificationsActivees() ? VERT + "Activées" + RESET : ROUGE + "Désactivées" + RESET));
        System.out.println("2. Temps de rappel : " + pref.getMinutesRappelAvant() + " minutes");
        System.out.println("3. Thème : " + pref.getTheme());
        System.out.println("4. Langue : " + pref.getLanguePrefere());
        System.out.println("5. Objectif d'étude : " + (pref.getTempsEtudeIdealParJour() / 60) + " heures");
        System.out.println("6. Énergie matin : " + pref.getEnergieMatin());
        System.out.println("7. Énergie après-midi : " + pref.getEnergieApresMidi());
        System.out.println("8. Énergie soir : " + pref.getEnergieSoir());
        System.out.println("0. Retour");

        System.out.print("Choix à modifier : ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                pref.setNotificationsActivees(!pref.isNotificationsActivees());
                System.out.println(VERT + "[✓] Notifications " + (pref.isNotificationsActivees() ? "activées" : "désactivées") + RESET);
                break;
            case "2":
                System.out.print("Temps de rappel (minutes) : ");
                pref.setMinutesRappelAvant(Integer.parseInt(scanner.nextLine().trim()));
                System.out.println(VERT + "[✓] Mis à jour" + RESET);
                break;
            case "3":
                System.out.print("Thème (CLAIR/SOMBRE) : ");
                pref.setTheme(scanner.nextLine().trim());
                System.out.println(VERT + "[✓] Thème mis à jour" + RESET);
                break;
            case "4":
                System.out.print("Langue (FRANCAIS/ARABE/ANGLAIS) : ");
                pref.setLanguePrefere(scanner.nextLine().trim());
                System.out.println(VERT + "[✓] Langue mise à jour" + RESET);
                break;
            case "5":
                System.out.print("Objectif d'étude (heures) : ");
                pref.setTempsEtudeIdealParJour(Integer.parseInt(scanner.nextLine().trim()) * 60);
                System.out.println(VERT + "[✓] Objectif mis à jour" + RESET);
                break;
            case "6":
                System.out.print("Énergie matin (ELEVÉ/MOYEN/FAIBLE) : ");
                pref.setEnergieMatin(scanner.nextLine().trim().toUpperCase());
                break;
            case "7":
                System.out.print("Énergie après-midi (ELEVÉ/MOYEN/FAIBLE) : ");
                pref.setEnergieApresMidi(scanner.nextLine().trim().toUpperCase());
                break;
            case "8":
                System.out.print("Énergie soir (ELEVÉ/MOYEN/FAIBLE) : ");
                pref.setEnergieSoir(scanner.nextLine().trim().toUpperCase());
                break;
            default:
                System.out.println("Retour au menu.");
        }
        app.getPreferenceService().sauvegarderPreference(pref);
    }

    private static void afficherTableauBordComplet() {
        System.out.println("\n" + VIOLET + GRAS + "╔══════════════════════════════════════════╗" + RESET);
        System.out.println(VIOLET + GRAS + "║     📊 TABLEAU DE BORD & STATISTIQUES     ║" + RESET);
        System.out.println(VIOLET + GRAS + "╚══════════════════════════════════════════╝" + RESET);

        app.displayUserDashboard(currentUserId);

        List<Tache> toutesTaches = app.getTaskService().obtenirTachesUtilisateur(currentUserId);
        int total = toutesTaches.size();
        int terminees = (int) toutesTaches.stream().filter(Tache::isCompletee).count();
        double taux = total > 0 ? (double) terminees / total * 100 : 0.0;

        System.out.println("\n" + CYAN + "--- STATISTIQUES DE TÂCHES ---" + RESET);
        System.out.println("📝 Tâches totales    : " + JAUNE + total + RESET);
        System.out.println("✅ Tâches terminées  : " + VERT + terminees + RESET);
        System.out.println("⏳ Tâches en attente : " + ROUGE + (total - terminees) + RESET);
        System.out.printf("📈 Taux de complétion : " + (taux == 100 ? VERT : taux > 50 ? JAUNE : ROUGE) + "%.2f%%" + RESET + "\n", taux);
    }

    private static LocalDate promptDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println(ROUGE + "[✗] Format invalide (yyyy-MM-dd)" + RESET);
            }
        }
    }

    private static LocalTime promptTime(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return LocalTime.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println(ROUGE + "[✗] Format invalide (HH:mm)" + RESET);
            }
        }
    }

    /**
     * Génère un QR code du planning actuel
     */
    private static void genererQRCodePlanning() {
        System.out.println("\n" + CYAN + "--- GÉNÉRER QR CODE DU PLANNING ---" + RESET);
        
        Planning planning = app.getUserPlanning(currentUserId);
        if (planning == null || planning.getElements().isEmpty()) {
            System.out.println(ROUGE + "[✗] Aucun planning disponible. Générez-en un d'abord." + RESET);
            return;
        }
        
        Utilisateur user = app.getUserService().obtenirUtilisateur(currentUserId);
        if (user == null) {
            System.out.println(ROUGE + "[✗] Utilisateur non trouvé." + RESET);
            return;
        }
        
        System.out.println(JAUNE + "⏳ Génération du QR code en cours..." + RESET);
        
        String qrCodePath = qrCodeService.genererQRCodePlanning(planning, user);
        
        if (qrCodePath != null) {
            System.out.println(VERT + "[✓] QR Code généré avec succès!" + RESET);
            qrCodeService.afficherInstructions(qrCodePath);
            qrCodeService.afficherQRCode(qrCodePath);
            
            System.out.println(CYAN + "📌 Fichiers générés:" + RESET);
            System.out.println("  • QR Code: " + qrCodePath);
            System.out.println("  • Planning HTML: plannings_html/" + planning.getId() + ".html");
        } else {
            System.out.println(ROUGE + "[✗] Erreur lors de la génération du QR code." + RESET);
        }
    }
}