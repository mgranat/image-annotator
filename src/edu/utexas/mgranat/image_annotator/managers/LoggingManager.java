package edu.utexas.mgranat.image_annotator.managers;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Utility class for handling application wide logging.
 *
 * @author mgranat
 */
public final class LoggingManager {
    /**
     * Handler for the log file.
     */
    private static Handler m_logFileHandler;

    static {
        try {
            m_logFileHandler = new FileHandler("%t/image_annotator_log.txt");
            m_logFileHandler.setFormatter(new SimpleFormatter());
        } catch (IOException ex) {
            MessageManager.showMessage("Error initializing logging");
        }
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private LoggingManager() {
    }

    /**
     * Get the configured logger for a class.
     *
     * @param className The name of the class
     * @return The logger for the class
     */
    public static Logger getLogger(final String className) {
        Logger logger = Logger.getLogger(className);
        logger.addHandler(m_logFileHandler);
        return logger;
    }
}
