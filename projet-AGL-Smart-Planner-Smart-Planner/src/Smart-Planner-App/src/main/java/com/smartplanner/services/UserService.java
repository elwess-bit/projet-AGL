package com.smartplanner.services;

import com.smartplanner.models.Utilisateur;
import com.smartplanner.utils.SimpleLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Service for managing users
 */
public class UserService {
    private static final SimpleLogger logger = SimpleLogger.getLogger(UserService.class);
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

    private List<Utilisateur> utilisateurs;

    public UserService() {
        this.utilisateurs = new ArrayList<>();
    }

    /**
     * Register a new user
     */
    public Utilisateur creerUtilisateur(String nom, String email, String motDePasse, String niveauEtude) {
        if (nom == null || nom.isBlank()) {
            logger.warn("User name cannot be empty");
            return null;
        }
        if (!isEmailValid(email)) {
            logger.warn("Invalid email format: {}", email);
            return null;
        }
        if (!isPasswordValid(motDePasse)) {
            logger.warn("Password does not meet minimum requirements");
            return null;
        }

        Utilisateur existing = utilisateurs.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            logger.warn("User with email {} already exists", email);
            return null;
        }

        Utilisateur utilisateur = new Utilisateur(nom, email, motDePasse, niveauEtude);
        utilisateurs.add(utilisateur);
        logger.info("New user created: {} ({})", nom, email);
        return utilisateur;
    }

    /**
     * Get user by ID
     */
    public Utilisateur obtenirUtilisateur(String utilisateurId) {
        return utilisateurs.stream()
                .filter(u -> u.getId().equals(utilisateurId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Get user by email
     */
    public Utilisateur obtenirUtilisateurParEmail(String email) {
        return utilisateurs.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    public Utilisateur authentifierUtilisateur(String email, String motDePasse) {
        return utilisateurs.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email) && u.getMotDePasse().equals(motDePasse))
                .findFirst()
                .orElse(null);
    }

    /**
     * Update user profile
     */
    public Utilisateur mettreAJourUtilisateur(String utilisateurId, String nom, String niveauEtude) {
        Utilisateur utilisateur = obtenirUtilisateur(utilisateurId);

        if (utilisateur != null) {
            utilisateur.setNom(nom);
            utilisateur.setNiveauEtude(niveauEtude);
            logger.info("User {} profile updated", utilisateurId);
        }

        return utilisateur;
    }

    /**
     * Delete user
     */
    public void supprimerUtilisateur(String utilisateurId) {
        utilisateurs.removeIf(u -> u.getId().equals(utilisateurId));
        logger.info("User {} deleted", utilisateurId);
    }

    /**
     * Get all users
     */
    public List<Utilisateur> obtenirTousUtilisateurs() {
        return new ArrayList<>(utilisateurs);
    }

    /**
     * Get user count
     */
    public int compterUtilisateurs() {
        return utilisateurs.size();
    }

    private boolean isEmailValid(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    private boolean isPasswordValid(String motDePasse) {
        return motDePasse != null && motDePasse.length() >= 6;
    }
}
