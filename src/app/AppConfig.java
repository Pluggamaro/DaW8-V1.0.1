package app;

import java.io.*;
import java.util.Properties;

public class AppConfig {
    private static final String CONFIG_FILE_PATH = "config.properties";
    private static Properties properties;

    private static boolean directorySettingDone = false;
    
    // Initializing properties
    static {
        properties = new Properties();
        loadProperties();
    }

 // Helper method to load properties from the configuration file
    private static void loadProperties() {
        try {
            File configFile = new File(CONFIG_FILE_PATH);

            if (!configFile.exists()) {
                // Create the file if it doesn't exist
                configFile.createNewFile();
                // Default properties
                properties.setProperty("initialized", "false");
                properties.setProperty("admin.username", "");
                properties.setProperty("admin.password", "");
                properties.setProperty("default.location", "user.home" );
                saveProperties(); 
            } else {
                properties.load(new FileInputStream(configFile));
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
    
    public static boolean isDirectorySettingDone() {
        return directorySettingDone;
    }

    public static void setDirectorySettingDone(boolean done) {
        directorySettingDone = done;
        properties.setProperty("initialized", String.valueOf(done));
        saveProperties(); 
    }


    
    private static void saveProperties() {
        try {
            properties.store(new FileOutputStream(CONFIG_FILE_PATH), null);
        } catch (IOException e) {
            e.printStackTrace(); // Handle file saving error
        }
    }


    public static boolean isInitialized() {
        return Boolean.parseBoolean(properties.getProperty("initialized", "false"));
    }

    
    public static void setInitialized(boolean initialized) {
        properties.setProperty("initialized", String.valueOf(initialized));
        saveProperties(); // Save the updated properties to the file
    }

    
    public static String getAdminUsername() {
        return properties.getProperty("admin.username", "");
    }

    
    public static String getAdminPassword() {
        return properties.getProperty("admin.password", "");
    }

    
    public static void setAdminUsername(String username) {
        properties.setProperty("admin.username", username);
        saveProperties(); 
    }


    public static void setAdminPassword(String password) {
        properties.setProperty("admin.password", password);
        saveProperties(); 
    }
    
    
 
    public static String getDefaultLocation() {
        return properties.getProperty("default.location", System.getProperty("user.home"));
    }

    
    public static void setDefaultLocation(String defaultLocation) {
        properties.setProperty("default.location", defaultLocation);
        saveProperties(); 
    }
    
    
}
