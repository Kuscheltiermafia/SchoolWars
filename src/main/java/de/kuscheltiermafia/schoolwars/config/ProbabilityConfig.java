package de.kuscheltiermafia.schoolwars.config;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Centralized probability configuration manager.
 * Loads and provides access to all probability values used throughout the game.
 */
public class ProbabilityConfig {
    
    private static Properties properties;
    private static boolean initialized = false;
    
    /**
     * Initialize the probability configuration.
     * Should be called once during plugin startup.
     */
    public static void initialize(JavaPlugin plugin) {
        if (initialized) {
            return;
        }
        
        properties = new Properties();
        
        try (InputStream input = plugin.getResource("probabilities.properties")) {
            if (input == null) {
                plugin.getLogger().severe("Could not find probabilities.properties file!");
                return;
            }
            
            properties.load(input);
            initialized = true;
            plugin.getLogger().info("Loaded probability configuration with " + properties.size() + " entries");
            
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Error loading probabilities.properties", e);
        }
    }
    
    /**
     * Get a double probability value from the configuration.
     * 
     * @param key The configuration key
     * @param defaultValue Default value if key is not found
     * @return The probability value
     */
    public static double getProbability(String key, double defaultValue) {
        if (!initialized || properties == null) {
            return defaultValue;
        }
        
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * Get an integer value from the configuration.
     * 
     * @param key The configuration key
     * @param defaultValue Default value if key is not found
     * @return The integer value
     */
    public static int getInteger(String key, int defaultValue) {
        if (!initialized || properties == null) {
            return defaultValue;
        }
        
        String value = properties.getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * Check if the configuration is properly initialized.
     * 
     * @return true if initialized, false otherwise
     */
    public static boolean isInitialized() {
        return initialized;
    }
}