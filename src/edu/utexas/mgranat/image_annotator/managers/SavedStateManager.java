package edu.utexas.mgranat.image_annotator.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for storing saved state.
 *
 * @author mgranat
 */
public final class SavedStateManager {
    /**
     * Logger for this class.
     */
    private static Logger m_logger =
            LoggingManager.getLogger(SavedStateManager.class.getName());

    /**
     * Stores the default properties.
     */
    private static Properties m_defaultProperties;

    static {
        m_defaultProperties = new Properties();

        /* Add other state here */
        m_defaultProperties.setProperty("app.last_dir",
                System.getProperty("user.home"));
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private SavedStateManager() {
    }

    /**
     * Stores the location of the saved state file.
     */
    private static final File SAVED_STATE_DIR =
            new File(System.getenv("APPDATA") + File.separator
                    + "Image Annotator" + File.separator);

    /**
     * Stores the name of the saved state file.
     */
    private static final String SAVED_STATE_NAME = "saved_state.properties";

    /**
     * Stores the current properties.
     */
    private static Properties m_currentProperties;

    /**
     * Stores whether the state has been altered since load.
     */
    private static boolean m_altered;

    /**
     * Checks to see if a saved state file exists for the application.
     *
     * @return True if a saved state file exists, false otherwise
     */
    public static boolean existsSavedStateFile() {
        return new File(SAVED_STATE_DIR, SAVED_STATE_NAME).exists();
    }

    /**
     * Creates a saved state file with all options set to default.
     */
    public static void createDefaultSavedStateFile() {
        if (!SAVED_STATE_DIR.exists()) {
            SAVED_STATE_DIR.mkdirs();
        }

        try {
            m_defaultProperties.store(new FileOutputStream(
                    new File(SAVED_STATE_DIR, SAVED_STATE_NAME)),
                    "#### Image Annotator SAVED STATEs #####");
        } catch (IOException ex) {
            m_logger.log(Level.WARNING, "Error writing to saved state file",
                    ex);
            MessageManager.showMessage("Error writing to saved state file");
        }
    }

    /**
     * Read a property that will persist after the application closes.
     *
     * @param key The key of the property to read
     * @return The value of the property
     */
    public static String getProperty(final String key) {
        return m_currentProperties.getProperty(key);
    }

    /**
     * Set a property that will be persisted after the application closes.
     *
     * @param key The key of the property to set
     * @param value The value of the property to set
     */
    public static void setProperty(final String key, final String value) {
        m_currentProperties.setProperty(key, value);

        m_altered = true;
    }

    /**
     * Load saved state.
     */
    public static void loadProperties() {
        m_currentProperties = new Properties(m_defaultProperties);

        try {
            m_currentProperties.load(new FileInputStream(
                    new File(SAVED_STATE_DIR, SAVED_STATE_NAME)));
        } catch (IOException ex) {
            m_logger.log(Level.WARNING,
                    "Error reading from saved state file, using defaults", ex);
            MessageManager.showMessage(
                    "Error reading from saved state file, using defaults");
            m_currentProperties = m_defaultProperties;
        }

        validateProperties();
    }

    /**
     * Validate a loaded saved state.
     */
    private static void validateProperties() {
        String key, value;
        boolean altered = false;

        /* Valid dir */
        key = "app.last_dir";
        value = m_currentProperties.getProperty(key);

        if (!SavedStateManager.isValidDirectory(value)) {
            m_logger.log(Level.INFO, "Invalid saved state detected, \""
                    + key + "\" must be a valid directory");
            m_currentProperties.setProperty(key,
                    m_defaultProperties.getProperty(key));
            altered = true;
        }

        if (altered) {
            m_logger.log(Level.INFO, "Invalid saved state detected, "
                    + "overwriting offending values with defaults");
            SavedStateManager.writeNewSavedState();
        }
    }

    /**
     * Checks if the provided string is a valid directory.
     *
     * @param property The string to be checked
     * @return True if the string is a valid directory
     */
    private static boolean isValidDirectory(final String property) {
        return new File(property).exists();
    }

    /**
     * Writes new saved state if altered since load.
     */
    public static void saveStateIfAltered() {
        if (m_altered) {
            SavedStateManager.writeNewSavedState();
        }
    }

    /**
     * Writes the current properties to file.
     */
    private static void writeNewSavedState() {
        m_logger.log(Level.INFO, "Creating new saved state file");

        if (!SAVED_STATE_DIR.exists()) {
            SAVED_STATE_DIR.mkdirs();
        }

        try {
            m_currentProperties.store(new FileOutputStream(
                    new File(SAVED_STATE_DIR, SAVED_STATE_NAME)),
                    "#### Image Annotator saved state #####");
        } catch (IOException ex) {
            m_logger.log(Level.WARNING, "Error writing to saved state file",
                    ex);
            MessageManager.showMessage("Error writing to saved state file");
        }
    }
}
