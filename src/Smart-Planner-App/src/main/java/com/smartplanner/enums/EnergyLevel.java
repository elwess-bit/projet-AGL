package com.smartplanner.enums;

/**
 * Energy level profiles for daily periods
 */
public enum EnergyLevel {
    ELEVE("ELEVÉ"),
    MOYEN("MOYEN"),
    FAIBLE("FAIBLE");

    private final String label;

    EnergyLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static EnergyLevel fromLabel(String label) {
        for (EnergyLevel level : values()) {
            if (level.label.equalsIgnoreCase(label.replace("É", "E").replace("é", "e"))) {
                return level;
            }
        }
        return MOYEN;
    }
}
