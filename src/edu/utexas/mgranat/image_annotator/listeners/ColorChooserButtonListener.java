package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

import edu.utexas.mgranat.image_annotator.managers.SelectionManager;

/**
 * Listener for the color chooser button.
 *
 * @author mgranat
 */
public class ColorChooserButtonListener implements ActionListener {
    @Override
    public final void actionPerformed(final ActionEvent e) {
        Color chosenColor = JColorChooser.showDialog(null, "Choose a color",
                SelectionManager.getSelectedColor());
        if (chosenColor != null) {
            SelectionManager.setColor(chosenColor);
        }
    }
}
