package edu.utexas.mgranat.image_annotator;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;

import edu.utexas.mgranat.image_annotator.file_choosers.AnnotationFileFilter;
import edu.utexas.mgranat.image_annotator.file_choosers.ImageFileFilter;
import edu.utexas.mgranat.image_annotator.managers.ConfigManager;
import edu.utexas.mgranat.image_annotator.managers.ImageManager;
import edu.utexas.mgranat.image_annotator.managers.LoggingManager;
import edu.utexas.mgranat.image_annotator.managers.MessageManager;
import edu.utexas.mgranat.image_annotator.managers.SavedStateManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

public final class ImageAnnotator {
    private static Logger m_logger = LoggingManager.getLogger(ImageAnnotator.class.getName());

    private ImageAnnotator() {};

    /**
     * Start the application. The main method performs the following operations:
     *  - Set the look and feel
     *  - Parse command line arguments for an image to load
     *  - Load the configuration, creating a default one if none is found
     *  - Load the saved state, creating a default one if none is found
     *  - Initialize the application
     *  - Load the image specified in the command line arguments, if any
     *
     * @param args Command line arguments
     */
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            m_logger.log(Level.INFO, "Set look and feel to system value");
        } catch (Exception ex) {
            m_logger.log(Level.WARNING, "Unable to initialize look and feel", ex);
            MessageManager.showMessage("Error initializing application look and feel");
        }

        File toLoad = null;
        if (args.length > 0) {
            String filepath = args[0];
            File file = new File(filepath);

            if (file.exists() && new ImageFileFilter().accept(file)) {
                toLoad = file;
            }
            
            if (file.exists() && new AnnotationFileFilter().accept(file)) {
            	String name = file.getName().substring(0, file.getName().lastIndexOf('.'));
            	File directory = file.getParentFile();
            	for (File f : directory.listFiles(new ImageFileFilter())) {
            		if (f.getName().substring(0, f.getName().lastIndexOf('.')).equals(name)) {
            			toLoad = f;
            			break;
            		}
            	}
            }
        }

        if (!ConfigManager.existsConfigFile()) {
            m_logger.log(Level.INFO, "No config file found, creating one with default values");
            ConfigManager.createDefaultConfigFile();
        }

        ConfigManager.loadConfiguration();

        if (!SavedStateManager.existsSavedStateFile()) {
            m_logger.log(Level.INFO, "No saved state file found, creating one with default values");
            SavedStateManager.createDefaultSavedStateFile();
        }

        SavedStateManager.loadProperties();

        SingletonManager.init();

        if (toLoad != null) {
            m_logger.log(Level.INFO, "Command line argument detected, loading file");
            ImageManager.updateImage(toLoad);
        }
    }
}
