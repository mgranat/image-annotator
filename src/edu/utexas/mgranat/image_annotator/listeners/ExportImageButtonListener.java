package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import edu.utexas.mgranat.image_annotator.managers.ImageManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

/**
 * Listener for the export image button.
 *
 * @author mgranat
 */
public class ExportImageButtonListener implements ActionListener {
    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (!SingletonManager.getImagePanel().hasImage()) {
            return;
        }

        ImageManager.exportImages(new File[] {SingletonManager.getImagePanel()
                .getImageFile()});
    }
}
