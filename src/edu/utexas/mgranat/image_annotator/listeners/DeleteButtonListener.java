package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.utexas.mgranat.image_annotator.managers.LoggingManager;
import edu.utexas.mgranat.image_annotator.managers.MessageManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

/**
 * Listener for the delete button.
 *
 * @author mgranat
 */
public class DeleteButtonListener implements ActionListener {
    /**
     * Logger for this class.
     */
    private static Logger m_logger =
            LoggingManager.getLogger(DeleteButtonListener.class.getName());

    @Override
    public final void actionPerformed(final ActionEvent e) {
        try {
            SingletonManager.getAnnotationsPanel().delete(
                    SingletonManager.getAnnotationsPanel().getAnnList()
                            .getSelectedIndices());
        } catch (IllegalStateException ex) {
            m_logger.log(Level.WARNING,
                    "Unexpected error deleting annotations", ex);
            MessageManager.showMessage("Unexpected error deleting annotations");
        }
    }
}
