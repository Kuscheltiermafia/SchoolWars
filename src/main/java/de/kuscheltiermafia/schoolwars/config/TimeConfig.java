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
 * Centralized time configuration manager.
 * Loads and provides access to all time/duration values used throughout the game.
 * First tries to load from external config file in plugin data folder,
 * falls back to internal resource if not found.
 */
public class TimeConfig {
    
    private static Properties properties;
    private static boolean initialized = false;
    private static JavaPlugin pluginInstance;
    
    /**
     * Initialize the time configuration.
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
        File externalConfigFile = new File(dataFolder, "times.properties");
        
        boolean loadedFromExternal = false;
        
        if (externalConfigFile.exists()) {
            try (FileInputStream input = new FileInputStream(externalConfigFile)) {
                properties.load(input);
                loadedFromExternal = true;
                plugin.getLogger().info("Loaded time configuration from external file: " + externalConfigFile.getPath());
            } catch (IOException e) {
                plugin.getLogger().log(Level.WARNING, "Error loading external times.properties, falling back to internal resource", e);
            }
        }
        
        // If external file doesn't exist or failed to load, use internal resource
        if (!loadedFromExternal) {
            try (InputStream input = plugin.getResource("times.properties")) {
                if (input == null) {
                    plugin.getLogger().severe("Could not find times.properties file!");
                    return;
                }
                
                properties.load(input);
                plugin.getLogger().info("Loaded time configuration from internal resource");
                
                // Create external config file for easy editing
                createExternalConfigFile(plugin, externalConfigFile);
                
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Error loading times.properties", e);
                return;
            }
        }
        
        initialized = true;
        plugin.getLogger().info("Time configuration loaded with " + properties.size() + " entries");
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
            try (InputStream input = plugin.getResource("times.properties")) {
                if (input != null) {
                    Files.copy(input, externalConfigFile.toPath());
                    plugin.getLogger().info("Created external config file: " + externalConfigFile.getPath());
                    plugin.getLogger().info("You can now edit this file to modify time values without recompiling the plugin");
                }
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Could not create external config file", e);
        }
    }

    /**
     * Get an integer time value from the configuration.
     * 
     * @param key The configuration key
     * @param defaultValue Default value if key is not found
     * @return The time value in ticks
     */
    public static int getTicks(String key, int defaultValue) {
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
     * Get a time value in seconds (converts from ticks).
     * 
     * @param key The configuration key
     * @param defaultTicks Default value in ticks if key is not found
     * @return The time value in seconds
     */
    public static int getSeconds(String key, int defaultTicks) {
        return getTicks(key, defaultTicks) / 20;
    }
    
    /**
     * Reload the configuration from the external file.
     * Useful for applying changes without restarting the server.
     */
    public static void reload() {
        if (!initialized || pluginInstance == null) {
            return;
        }
        
        File externalConfigFile = new File(pluginInstance.getDataFolder(), "times.properties");
        
        if (externalConfigFile.exists()) {
            try (FileInputStream input = new FileInputStream(externalConfigFile)) {
                properties.clear();
                properties.load(input);
                pluginInstance.getLogger().info("Reloaded time configuration from external file");
            } catch (IOException e) {
                pluginInstance.getLogger().log(Level.WARNING, "Error reloading external times.properties", e);
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
