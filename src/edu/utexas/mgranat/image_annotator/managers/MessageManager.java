package edu.utexas.mgranat.image_annotator.managers;

import javax.swing.JOptionPane;

/**
 * Utility class for displaying messages to the user.
 *
 * @author mgranat
 */
public final class MessageManager {
    /**
     * Private constructor to prevent instantiation.
     */
    private MessageManager() {
    }

    /**
     * Display a message to the user.
     *
     * @param message The message to display
     */
    public static void showMessage(final Object message) {
        JOptionPane.showMessageDialog(null,
                message);
    }
}
