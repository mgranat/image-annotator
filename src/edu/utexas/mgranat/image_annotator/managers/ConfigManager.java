package edu.utexas.mgranat.image_annotator.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides access to configuation information.
 *
 * @author mgranat
 */
public final class ConfigManager {
    /**
     * Logger for this class.
     */
    private static Logger m_logger =
            LoggingManager.getLogger(ConfigManager.class.getName());

    /**
     * Stores the default properties.
     */
    private static Properties m_defaultProperties;

    static {
        m_defaultProperties = new Properties();

        /* Add properties here */
        m_defaultProperties.setProperty("annotations.outline.spacing", "10");
        m_defaultProperties.setProperty("annotations.thickness", ".005");
        m_defaultProperties.setProperty("window.annotations_panel.size", "100");
        m_defaultProperties.setProperty("window.width", "1280");
        m_defaultProperties.setProperty("window.height", "720");
        m_defaultProperties.setProperty("window.zoom.min", "-20");
        m_defaultProperties.setProperty("window.zoom.max", "10");
        m_defaultProperties.setProperty("window.zoom.multiplier", "1.2");
        m_defaultProperties.setProperty("window.start_maximized", "true");
    }

    /**
     * Stores the location of the config file.
     */
    private static final File CONFIG_DIR =
            new File(System.getenv("APPDATA") + File.separator
                    + "Image Annotator" + File.separator);

    /**
     * Stores the name of the config file.
     */
    private static final String CONFIG_NAME = "config.properties";

    /**
     * Stores the current properties.
     */
    private static Properties m_currentProperties;

    /**
     * Stores whether the configuration has been altered since load.
     */
    private static boolean m_altered;

    /**
     * Private constructor to prevent instantiation.
     */
    private ConfigManager() {
    }

    /**
     * Checks to see if a configuration file exists for the application.
     *
     * @return True if a config file exists, false otherwise
     */
    public static boolean existsConfigFile() {
        return new File(CONFIG_DIR, CONFIG_NAME).exists();
    }

    /**
     * Creates a config file with all options set to default.
     */
    public static void createDefaultConfigFile() {
        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
        }

        try {
            m_defaultProperties.store(new FileOutputStream(
                    new File(CONFIG_DIR, CONFIG_NAME)),
                    "#### Image Annotator configuration #####");
        } catch (IOException ex) {
            m_logger.log(Level.WARNING, "Error writing to config file", ex);
            MessageManager.showMessage("Error writing to config file");
        }
    }

    /**
     * Load a configuration.
     */
    public static void loadConfiguration() {
        m_currentProperties = new Properties(m_defaultProperties);

        try {
            m_currentProperties.load(new FileInputStream(
                    new File(CONFIG_DIR, CONFIG_NAME)));
        } catch (IOException ex) {
            m_logger.log(Level.WARNING,
                    "Error reading from config file, using defaults", ex);
            MessageManager.showMessage(
                    "Error reading from config file, using defaults");
            m_currentProperties = m_defaultProperties;
        }

        validateProperties();
    }

    /**
     * Validate the loaded properties and overwrites invalid values with
     * defaults.
     */
    private static void validateProperties() {
        String key, value;
        boolean altered = false;

        /* Integer, non-negative */
        key = "annotations.outline.spacing";
        value = m_currentProperties.getProperty(key);

        if (!(ConfigManager.isInteger(value)
                && ConfigManager.isNonNegativeInteger(value))) {
            m_logger.log(Level.INFO, "Invalid configuration detected, \""
                    + key + "\" must be a non-negative integer");
            m_currentProperties.setProperty(key,
                    m_defaultProperties.getProperty(key));
            altered = true;
        }

        /* Float, non-negative */
        key = "annotations.thickness";
        value = m_currentProperties.getProperty(key);

        if (!(ConfigManager.isFloat(value)
                && ConfigManager.isNonNegativeFloat(value))) {
            m_logger.log(Level.INFO, "Invalid configuration detected, \""
                    + key + "\" must be a non-negative number");
            m_currentProperties.setProperty(key,
                    m_defaultProperties.getProperty(key));
            altered = true;
        }

        /* Integer, non-negative */
        key = "window.annotations_panel.size";
        value = m_currentProperties.getProperty(key);

        if (!(ConfigManager.isInteger(value)
                && ConfigManager.isNonNegativeInteger(value))) {
            m_logger.log(Level.INFO, "Invalid configuration detected, \""
                    + key + "\" must be a non-negative integer");
            m_currentProperties.setProperty(key,
                    m_defaultProperties.getProperty(key));
            altered = true;
        }

        /* Integer, non-negative */
        key = "window.width";
        value = m_currentProperties.getProperty(key);

        if (!(ConfigManager.isInteger(value)
                && ConfigManager.isNonNegativeInteger(value))) {
            m_logger.log(Level.INFO, "Invalid configuration detected, \""
                    + key + "\" must be a non-negative integer");
            m_currentProperties.setProperty(key,
                    m_defaultProperties.getProperty(key));
            altered = true;
        }

        /* Integer, non-negative */
        key = "window.height";
        value = m_currentProperties.getProperty(key);

        if (!(ConfigManager.isInteger(value)
                && ConfigManager.isNonNegativeInteger(value))) {
            m_logger.log(Level.INFO, "Invalid configuration detected, \""
                    + key + "\" must be a non-negative integer");
            m_currentProperties.setProperty(key,
                    m_defaultProperties.getProperty(key));
            altered = true;
        }

        /* Integer */
        key = "window.zoom.min";
        value = m_currentProperties.getProperty(key);

        if (!ConfigManager.isInteger(value)) {
            m_logger.log(Level.INFO, "Invalid configuration detected, \""
                    + key + "\" must be an integer");
            m_currentProperties.setProperty(key,
                    m_defaultProperties.getProperty(key));
            altered = true;
        }

        /* Integer, non-negative */
        key = "window.zoom.max";
        value = m_currentProperties.getProperty(key);

        if (!(ConfigManager.isInteger(value)
                && ConfigManager.isNonNegativeInteger(value))) {
            m_logger.log(Level.INFO, "Invalid configuration detected, \""
                    + key + "\" must be a non-negative integer");
            m_currentProperties.setProperty(key,
                    m_defaultProperties.getProperty(key));
            altered = true;
        }

        /* Floating point number, non-negative */
        key = "window.zoom.multiplier";
        value = m_currentProperties.getProperty(key);

        if (!(ConfigManager.isFloat(value)
                && ConfigManager.isNonNegativeFloat(value))) {
            m_logger.log(Level.INFO, "Invalid configuration detected, \""
                    + key + "\" must be a non-negative number");
            m_currentProperties.setProperty(key,
                    m_defaultProperties.getProperty(key));
            altered = true;
        }
        
        /* Boolean */
        key = "window.start_maximized";
        value = m_currentProperties.getProperty(key);
        
        if (!ConfigManager.isBoolean(value)) {
            m_logger.log(Level.INFO, "Invalid configuration detected, \""
                    + key + "\" must be a boolean value");
            m_currentProperties.setProperty(key,
                    m_defaultProperties.getProperty(key));
            altered = true;
        }

        if (altered) {
            m_logger.log(Level.INFO, "Invalid configuration detected, "
                    + "overwriting offending values with defaults");
            writeNewConfig();
        }
    }

    /**
     * Checks if the provided string is a valid integer.
     *
     * @param property The string to be checked
     * @return True if the string is an integer, false otherwise
     */
    private static boolean isInteger(final String property) {
        try {
            Integer.parseInt(property);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the provided string is a valid floating point number.
     *
     * @param property The string to be checked
     * @return True if the string is a floating point number, false otherwise
     */
    private static boolean isFloat(final String property) {
        try {
            Double.parseDouble(property);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * Checks that the provided string is non-negative. Assumes that the string
     * is a valid integer.
     *
     * @param property The string to be checked
     * @return True if the string is non-negative, false otherwise
     */
    private static boolean isNonNegativeInteger(final String property) {
        return Integer.parseInt(property) >= 0;
    }

    /**
     * Checks that the provided string is non-negative. Assumes that the string
     * is a valid floating  point number.
     *
     * @param property The string to be checked
     * @return True if the string is non-negative, false otherwise
     */
    private static boolean isNonNegativeFloat(final String property) {
        return Double.parseDouble(property) >= 0;
    }
    
    private static boolean isBoolean(final String property) {
    	return (property.equals("true") || property.equals("false"));
    }

    /**
     * Writes the current properties to file.
     */
    private static void writeNewConfig() {
        m_logger.log(Level.INFO, "Creating new configuration file");

        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
        }

        try {
            m_currentProperties.store(new FileOutputStream(
                    new File(CONFIG_DIR, CONFIG_NAME)),
                    "#### Image Annotator configuration #####");
        } catch (IOException ex) {
            m_logger.log(Level.WARNING, "Error writing to config file", ex);
            MessageManager.showMessage("Error writing to config file");
        }
    }

    /**
     * Return a validated value for the provided key.
     *
     * @param key The name of the property
     * @return A validated value for the provided key
     */
    public static String getProperty(final String key) {
    	String property = m_currentProperties.getProperty(key);
    	if (property == null)
    		property = m_defaultProperties.getProperty(key);
    		if (property != null) {
    			m_currentProperties.setProperty(key, property);
    			m_altered = true;
    		}
        return property;
    }

    /**
     * Writes new config if altered since load.
     */
    public static void saveConfigIfAltered() {
        if (m_altered) {
            ConfigManager.writeNewConfig();
        }
    }
    
    /**
     * Gets the config file
     * @return The config file
     */
    public static File getConfigFile() {
    	return new File(CONFIG_DIR, CONFIG_NAME);
    }
}
