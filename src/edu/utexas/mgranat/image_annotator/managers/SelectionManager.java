package edu.utexas.mgranat.image_annotator.managers;

import java.awt.Color;
import java.util.HashSet;

import edu.utexas.mgranat.image_annotator.annotations.IAnnotation;

/**
 * Interface for obtaining info about user selections.
 *
 * @author mgranat
 */
public final class SelectionManager {
    /**
     * Private constructor to prevent instantiation.
     */
    private SelectionManager() { }

    /**
     * Retrieves the current selected annotation type.
     *
     * @return The selected annotation type
     */
    public static IAnnotation.Type getSelectedAnnotationType() {
        return SingletonManager.getButtonPanel().getSelectedAnnotationType();
    }

    /**
     * Retrieves the current selected color.
     *
     * @return The current selected color
     */
    public static Color getSelectedColor() {
        return SingletonManager.getButtonPanel().getSelectedColor();
    }

    /**
     * Set the current color.
     *
     * @param c
     *            The new color
     */
    public static void setColor(final Color c) {
        SingletonManager.getButtonPanel().setColor(c);
    }

    /**
     * Retrieves the current selected font size.
     *
     * @return The current selected font size
     */
    public static int getSelectedFontSize() {
        return SingletonManager.getButtonPanel().getSelectedFontSize();
    }

    /**
     * Checks if bold is selected.
     *
     * @return True if bold is selected
     */
    public static boolean isBoldSelected() {
        return SingletonManager.getButtonPanel().isBoldSelected();
    }

    /**
     * Checks if italics is selected.
     *
     * @return True if italics is selected
     */
    public static boolean isItalicsSelected() {
        return SingletonManager.getButtonPanel().isItalicsSelected();
    }

    /**
     * Retrieves the set of selected annotations.
     *
     * @return The set of selected annotations
     */
    public static HashSet<IAnnotation> getSelectedAnnotations() {
        return SingletonManager.getAnnotationsPanel().getSelectedAnnotations();
    }
}
