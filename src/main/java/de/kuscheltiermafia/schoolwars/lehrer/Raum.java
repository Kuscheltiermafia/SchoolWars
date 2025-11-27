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
    /** Standard classroom with no special requirements. */
    NORMAL,
    
    /** Art and crafts room. */
    KUNST,
    
    /** Physics laboratory and preparation rooms. */
    PHYSIK,
    
    /** Chemistry laboratory and preparation rooms. */
    CHEMIE,
    
    /** General areas accessible to all teachers (hallways, cafeteria, etc.). */
    GENERAL,
    
    /** Music room with instruments and equipment. */
    MUSIK,
    
    /** Upper secretary office area. */
    OS_SEKI,
    
    /** Administrative offices and staff areas. */
    VERWALTUNG,
    
    /** Glass display cases / exhibition areas. */
    GLASKASTEN;
}
