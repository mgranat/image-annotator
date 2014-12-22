package edu.utexas.mgranat.image_annotator;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import edu.utexas.mgranat.image_annotator.file_choosers.ImageFileFilter;
import edu.utexas.mgranat.image_annotator.managers.ConfigManager;
import edu.utexas.mgranat.image_annotator.managers.ImageManager;
import edu.utexas.mgranat.image_annotator.managers.LoggingManager;
import edu.utexas.mgranat.image_annotator.managers.MessageManager;
import edu.utexas.mgranat.image_annotator.managers.SavedStateManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

/**
 * Image Annotator application.
 *
 * @author mgranat
 */
public final class ImageAnnotator {
    /**
     * Logger for this class.
     */
    private static Logger m_logger =
            LoggingManager.getLogger(ImageAnnotator.class.getName());

    /**
     * Private constructor to prevent instantiation.
     */
    private ImageAnnotator() {
    };

    /**
     * Start the application. Create default properties if nonexistent.
     *
     * @param args Command line arguments
     */
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            m_logger.log(Level.INFO, "Set look and feel to system value");
        } catch (Exception ex) {
            m_logger.log(Level.WARNING,
                    "Unable to initialize look and feel", ex);
            MessageManager.showMessage(
                    "Error initializing application look and feel");
        }

        File toLoad = null;
        if (args.length > 0) {
            String filepath = args[0];
            File file = new File(filepath);

            if (file.exists() && new ImageFileFilter().accept(file)) {
                toLoad = file;
            }
        }

        if (!ConfigManager.existsConfigFile()) {
            m_logger.log(Level.INFO,
                    "No config file found, creating one with default values");
            ConfigManager.createDefaultConfigFile();
        }

        ConfigManager.loadConfiguration();

        if (!SavedStateManager.existsSavedStateFile()) {
            m_logger.log(Level.INFO,
                    "No saved state file found, creating one with default"
                    + "values");
            SavedStateManager.createDefaultSavedStateFile();
        }

        SavedStateManager.loadProperties();

        SingletonManager.init();

        if (toLoad != null) {
            m_logger.log(Level.INFO,
                    "Command line argument detected, loading file");
            ImageManager.updateImage(toLoad);
        }
    }
}
