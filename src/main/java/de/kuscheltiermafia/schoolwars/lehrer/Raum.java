package de.kuscheltiermafia.schoolwars.lehrer;

/**
 * Defines the different types of rooms/areas in the school.
 * <p>
 * Room types are used to match teachers to appropriate areas based on their subject
 * specialization. For example, a physics teacher would prefer PHYSIK rooms,
 * while a music teacher would prefer MUSIK rooms.
 * </p>
 */
public enum Raum {
    NORMAL,
    KUNST,
    PHYSIK,
    CHEMIE,
    GENERAL,
    MUSIK,
    OS_SEKI,
    VERWALTUNG,
    GLASKASTEN;
}
