package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import edu.utexas.mgranat.image_annotator.file_choosers.ImageFileFilter;
import edu.utexas.mgranat.image_annotator.managers.ImageManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

/**
 * Listener for previous button.
 *
 * @author mgranat
 */
public class PreviousButtonListener implements ActionListener {
    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (!SingletonManager.getImagePanel().hasImage()) {
            return;
        }

        File[] filesInDir = SingletonManager.getImagePanel().getImageFile()
                .getParentFile().listFiles(new ImageFileFilter());

        int i = 0;
        while (i < filesInDir.length
                && !SingletonManager.getImagePanel().getImageFile().getPath()
                        .equals(filesInDir[i].getPath())) {
            i++;
        }

        int outputFileIndex = 0;
        if (i == 0) {
            outputFileIndex = filesInDir.length - 1;
        } else {
            outputFileIndex = i - 1;
        }

        ImageManager.updateImage(filesInDir[outputFileIndex]);
    }
}
