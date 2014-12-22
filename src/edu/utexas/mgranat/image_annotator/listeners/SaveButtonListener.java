package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

/**
 * Listener for save button.
 *
 * @author mgranat
 */
public class SaveButtonListener implements ActionListener {
    @Override
    public final void actionPerformed(final ActionEvent e) {
        SingletonManager.getImagePanel().saveAnnotations();
    }
}
