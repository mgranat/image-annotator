package edu.utexas.mgranat.image_annotator.managers;

import java.awt.BorderLayout;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.utexas.mgranat.image_annotator.gui.AnnotationsPanel;
import edu.utexas.mgranat.image_annotator.gui.ButtonPanel;
import edu.utexas.mgranat.image_annotator.gui.ImageAnnotatorFrame;
import edu.utexas.mgranat.image_annotator.gui.ImagePanel;

/**
 * Provides instances for singletons that make up application components.
 *
 * @author mgranat
 */
public final class SingletonManager {
    /**
     * Logger for this class.
     */
    private static Logger m_logger =
            LoggingManager.getLogger(SingletonManager.class.getName());

    /**
     * Stores whether the application has been initialized.
     */
    private static boolean m_init = false;

    /**
     * Stores the ImageAnnotatorFrame instance.
     */
    private static ImageAnnotatorFrame m_imageAnnotatorFrame;

    /**
     * Stores the ImagePanel instance.
     */
    private static ImagePanel m_imagePanel;

    /**
     * Stores the AnnotationsPanel instance.
     */
    private static AnnotationsPanel m_annotationsPanel;

    /**
     * Stores the ButtonPanel instance.
     */
    private static ButtonPanel m_buttonPanel;

    /**
     * Private constructor to prevent instantiation.
     */
    private SingletonManager() { }

    /**
     * Set the title of the application.
     *
     * @param title The new title
     */
    public static void setTitle(final String title) {
        m_imageAnnotatorFrame.setTitle(title);
    }

    /**
     * Get the active ImagePanel instance.
     *
     * @return the active ImagePanel instance
     */
    public static ImagePanel getImagePanel() {
        return m_imagePanel;
    }

    /**
     * Get the active AnnotationsPanel instance.
     *
     * @return the active AnnotationsPanel instance
     */
    public static AnnotationsPanel getAnnotationsPanel() {
        return m_annotationsPanel;
    }

    /**
     * Get the active ButtonPanel instance.
     *
     * @return the active ButtonPanel instance
     */
    public static ButtonPanel getButtonPanel() {
        return m_buttonPanel;
    }

    /**
     * Create the singleton instances.
     */
    public static void init() {
        if (m_init) {
            return;
        }
        m_logger.log(Level.INFO, "Initializing GUI components");

        m_imageAnnotatorFrame = new ImageAnnotatorFrame();

        m_imagePanel = new ImagePanel();
        m_annotationsPanel = new AnnotationsPanel();
        m_buttonPanel = new ButtonPanel();

        m_imageAnnotatorFrame.getContentPane().add(m_imagePanel,
                BorderLayout.CENTER);
        m_imageAnnotatorFrame.getContentPane().add(m_annotationsPanel,
                BorderLayout.WEST);
        m_imageAnnotatorFrame.getContentPane().add(m_buttonPanel,
                BorderLayout.NORTH);

        m_imageAnnotatorFrame.setVisible(true);

        m_init = true;
    }
}
