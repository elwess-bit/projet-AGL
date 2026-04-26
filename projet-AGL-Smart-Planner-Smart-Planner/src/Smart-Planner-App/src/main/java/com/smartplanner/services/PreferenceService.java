package com.smartplanner.services;

import com.smartplanner.models.Preference;
import com.smartplanner.utils.SimpleLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for managing user preferences
 */
public class PreferenceService {
    private static final SimpleLogger logger = SimpleLogger.getLogger(PreferenceService.class);

    private List<Preference> preferences;

    public PreferenceService() {
        this.preferences = new ArrayList<>();
    }

    /**
     * Create or get default preference for a user
     */
    public Preference sauvegarderPreference(Preference preference) {
        // Check if preference already exists
        Preference existing = preferences.stream()
                .filter(p -> p.getUtilisateurId().equals(preference.getUtilisateurId()))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            // Update existing
            existing.setNotificationsActivees(preference.isNotificationsActivees());
            existing.setMinutesRappelAvant(preference.getMinutesRappelAvant());
            existing.setTheme(preference.getTheme());
            existing.setLanguePrefere(preference.getLanguePrefere());
            existing.setTempsEtudeIdealParJour(preference.getTempsEtudeIdealParJour());
            existing.setActiverNotificationsRetard(preference.isActiverNotificationsRetard());
            logger.info("Preference updated for user {}", preference.getUtilisateurId());
            return existing;
        } else {
            // Add new
            preferences.add(preference);
            logger.info("New preference saved for user {}", preference.getUtilisateurId());
            return preference;
        }
    }

    /**
     * Get preference for a user
     */
    public Preference obtenirPreference(String utilisateurId) {
        Preference pref = preferences.stream()
                .filter(p -> p.getUtilisateurId().equals(utilisateurId))
                .findFirst()
                .orElse(null);

        // Return default if not found
        if (pref == null) {
            pref = new Preference(utilisateurId);
            preferences.add(pref);
            logger.debug("Default preference created for user {}", utilisateurId);
        }

        return pref;
    }

    /**
     * Update notification settings
     */
    public void activerNotifications(String utilisateurId, boolean actif) {
        Preference pref = obtenirPreference(utilisateurId);
        pref.setNotificationsActivees(actif);
        logger.info("Notifications {} for user {}", actif ? "enabled" : "disabled", utilisateurId);
    }

    /**
     * Update theme
     */
    public void changerTheme(String utilisateurId, String theme) {
        Preference pref = obtenirPreference(utilisateurId);
        pref.setTheme(theme);
        logger.debug("Theme changed to {} for user {}", theme, utilisateurId);
    }

    /**
     * Update language
     */
    public void changerLangue(String utilisateurId, String langue) {
        Preference pref = obtenirPreference(utilisateurId);
        pref.setLanguePrefere(langue);
        logger.debug("Language changed to {} for user {}", langue, utilisateurId);
    }

    /**
     * Update reminder time
     */
    public void changerTempsRappel(String utilisateurId, int minutesAvant) {
        Preference pref = obtenirPreference(utilisateurId);
        pref.setMinutesRappelAvant(minutesAvant);
        logger.debug("Reminder time set to {} minutes for user {}", minutesAvant, utilisateurId);
    }

    /**
     * Update ideal study time
     */
    public void changerTempsEtudeIdeal(String utilisateurId, int minutes) {
        Preference pref = obtenirPreference(utilisateurId);
        pref.setTempsEtudeIdealParJour(minutes);
        logger.debug("Ideal study time set to {} minutes for user {}", minutes, utilisateurId);
    }

    public List<Preference> getAllPreferences() {
        return new ArrayList<>(preferences);
    }
}
