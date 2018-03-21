package edu.utexas.mgranat.image_annotator.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.JFrame;

import edu.utexas.mgranat.image_annotator.listeners.AutosaveWindowAdapter;
import edu.utexas.mgranat.image_annotator.managers.ConfigManager;

/**
 * Frame for application.
 *
 * @author mgranat
 */
public class ImageAnnotatorFrame extends JFrame {
    /**
     * UID for serialization. Not used.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Stores the default width of the window.
     */
    private static final int WIDTH =
            Integer.parseInt(ConfigManager.getProperty("window.width"));

    /**
     * Stores the default height of the window.
     */
    private static final int HEIGHT =
            Integer.parseInt(ConfigManager.getProperty("window.height"));

    /**
     * Default dimension of the window.
     */
    private Dimension m_defaultSize =
            new Dimension(WIDTH, HEIGHT);

    /**
     * Set up frame for GUI.
     */
    public ImageAnnotatorFrame() {
        setTitle("Image Annotator");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        setVisible(true);
        if (ConfigManager.getProperty("window.start_maximized").equals("true")) {
        	setExtendedState(Frame.MAXIMIZED_BOTH);
        } else {
        	setSize(m_defaultSize);
        	setLocationRelativeTo(null);
        }
        
        getContentPane().setLayout(new BorderLayout());

        addWindowListener(new AutosaveWindowAdapter());
    }
}
