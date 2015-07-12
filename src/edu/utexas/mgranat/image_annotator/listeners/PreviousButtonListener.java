package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.utexas.mgranat.image_annotator.managers.ImageManager;

/**
 * Listener for previous button.
 *
 * @author mgranat
 */
public class PreviousButtonListener implements ActionListener {
    @Override
    public final void actionPerformed(final ActionEvent e) {
        ImageManager.previousImage();
    }
}
