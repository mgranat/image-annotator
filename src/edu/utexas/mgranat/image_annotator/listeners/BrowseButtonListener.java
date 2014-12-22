package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;

import edu.utexas.mgranat.image_annotator.file_choosers.ImageFileChooser;
import edu.utexas.mgranat.image_annotator.managers.ImageManager;
import edu.utexas.mgranat.image_annotator.managers.LoggingManager;
import edu.utexas.mgranat.image_annotator.managers.MessageManager;
import edu.utexas.mgranat.image_annotator.managers.SavedStateManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

/**
 * Listener for the browse button.
 *
 * @author mgranat
 */
public class BrowseButtonListener implements ActionListener {
    /**
     * Logger for this class.
     */
    private static Logger m_logger =
            LoggingManager.getLogger(BrowseButtonListener.class.getName());

    @Override
    public final void actionPerformed(final ActionEvent e) {
        JFileChooser browser = new ImageFileChooser(
                SavedStateManager.getProperty("app.last_dir"));
        browser.setDialogTitle("Open Image");
        int result = browser.showOpenDialog(SingletonManager.getImagePanel());

        if (result == JFileChooser.APPROVE_OPTION) {
            ImageManager.updateImage(browser.getSelectedFile());
            SavedStateManager.setProperty("app.last_dir",
                    browser.getSelectedFile().getParent());
        } else if (result == JFileChooser.ERROR_OPTION) {
            m_logger.log(Level.WARNING, "Unexpected error opening file");
            MessageManager.showMessage("Unexpected error opening file");
        }
    }
}
