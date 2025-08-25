package de.kuscheltiermafia.schoolwars.config;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Centralized probability configuration manager.
 * Loads and provides access to all probability values used throughout the game.
 * First tries to load from external config file in plugin data folder,
 * falls back to internal resource if not found.
 */
public class ProbabilityConfig {
    
    private static Properties properties;
    private static boolean initialized = false;
    private static JavaPlugin pluginInstance;
    
    /**
     * Initialize the probability configuration.
     * Should be called once during plugin startup.
     */
    public static void initialize(JavaPlugin plugin) {
        if (initialized) {
            return;
        }
        
        pluginInstance = plugin;
        properties = new Properties();
        
        // First try to load from external config file
        File dataFolder = plugin.getDataFolder();
        File externalConfigFile = new File(dataFolder, "probabilities.properties");
        
        boolean loadedFromExternal = false;
        
        if (externalConfigFile.exists()) {
            try (FileInputStream input = new FileInputStream(externalConfigFile)) {
                properties.load(input);
                loadedFromExternal = true;
                plugin.getLogger().info("Loaded probability configuration from external file: " + externalConfigFile.getPath());
            } catch (IOException e) {
                plugin.getLogger().log(Level.WARNING, "Error loading external probabilities.properties, falling back to internal resource", e);
            }
        }
        
        // If external file doesn't exist or failed to load, use internal resource
        if (!loadedFromExternal) {
            try (InputStream input = plugin.getResource("probabilities.properties")) {
                if (input == null) {
                    plugin.getLogger().severe("Could not find probabilities.properties file!");
                    return;
                }
                
                properties.load(input);
                plugin.getLogger().info("Loaded probability configuration from internal resource");
                
                // Create external config file for easy editing
                createExternalConfigFile(plugin, externalConfigFile);
                
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Error loading probabilities.properties", e);
                return;
            }
        }
        
        initialized = true;
        plugin.getLogger().info("Probability configuration loaded with " + properties.size() + " entries");
    }
    
    /**
     * Create an external config file copy for easy editing.
     */
    private static void createExternalConfigFile(JavaPlugin plugin, File externalConfigFile) {
        try {
            // Ensure data folder exists
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }
            
            // Copy internal resource to external file
            try (InputStream input = plugin.getResource("probabilities.properties")) {
                if (input != null) {
                    Files.copy(input, externalConfigFile.toPath());
                    plugin.getLogger().info("Created external config file: " + externalConfigFile.getPath());
                    plugin.getLogger().info("You can now edit this file to modify probabilities without recompiling the plugin");
                }
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Could not create external config file", e);
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
     * Reload the configuration from the external file.
     * Useful for applying changes without restarting the server.
     */
    public static void reload() {
        if (!initialized || pluginInstance == null) {
            return;
        }
        
        File externalConfigFile = new File(pluginInstance.getDataFolder(), "probabilities.properties");
        
        if (externalConfigFile.exists()) {
            try (FileInputStream input = new FileInputStream(externalConfigFile)) {
                properties.clear();
                properties.load(input);
                pluginInstance.getLogger().info("Reloaded probability configuration from external file");
            } catch (IOException e) {
                pluginInstance.getLogger().log(Level.WARNING, "Error reloading external probabilities.properties", e);
            }
        } else {
            pluginInstance.getLogger().warning("External config file does not exist: " + externalConfigFile.getPath());
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