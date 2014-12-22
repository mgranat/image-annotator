package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import edu.utexas.mgranat.image_annotator.managers.ConfigManager;
import edu.utexas.mgranat.image_annotator.managers.SavedStateManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

/**
 * Listener for autosaving annotations when the application exits.
 *
 * @author mgranat
 */
public class AutosaveWindowAdapter extends WindowAdapter {
    @Override
    public final void windowClosing(final WindowEvent ev) {
        SingletonManager.getImagePanel().saveAnnotations();
        ev.getWindow().dispose();

        ConfigManager.saveConfigIfAltered();

        SavedStateManager.saveStateIfAltered();
    }
}
