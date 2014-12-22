package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.utexas.mgranat.image_annotator.file_choosers.ImageFileFilter;
import edu.utexas.mgranat.image_annotator.managers.ImageManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

/**
 * Listener for the export folder button.
 *
 * @author mgranat
 */
public class ExportFolderButtonListener implements ActionListener {
    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (!SingletonManager.getImagePanel().hasImage()) {
            return;
        }

        ImageManager.exportImages(SingletonManager.getImagePanel()
                .getImageFile().getParentFile()
                .listFiles(new ImageFileFilter()));
    }
}
