package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

/**
 * Listener for detecting when the annotation selection changes and updating
 * the corresponding set.
 *
 * @author mgranat
 */
public class RepaintImagePanelListener implements ActionListener {
    @Override
    public final void actionPerformed(final ActionEvent e) {
        SingletonManager.getImagePanel().repaint();
    }
}
