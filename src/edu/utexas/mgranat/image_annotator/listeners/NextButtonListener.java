package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import edu.utexas.mgranat.image_annotator.file_choosers.ImageFileFilter;
import edu.utexas.mgranat.image_annotator.managers.ImageManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

/**
 * Listener for next button.
 *
 * @author mgranat
 */
public class NextButtonListener implements ActionListener {
    @Override
    public final void actionPerformed(final ActionEvent e) {
        ImageManager.nextImage();
    }
}
