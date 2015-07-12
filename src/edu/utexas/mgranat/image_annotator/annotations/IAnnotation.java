package edu.utexas.mgranat.image_annotator.annotations;

import java.awt.Graphics2D;

import edu.utexas.mgranat.image_annotator.managers.ConfigManager;

/**
 * Interface for defining annotation types.
 *
 * @author mgranat
 */
public interface IAnnotation {
    /**
    * Distance from annotation to outline in pixels.
    */
   int OUTLINE_SIZE = Integer.parseInt(
           ConfigManager.getProperty("annotations.outline.spacing"));

   /**
    * Enum for describing annotation types. This enum is used by
    * the combo box for selecting annotation types, so names should
    * not be shortened.
    *
    * @author mgranat
    */
    public static enum Type {
        /**
         * Circle annotation types.
         */
        Circle,

        /**
         * Text annotation types.
         */
        Text,

        /**
         * Rectangle annotation types.
         */
        Rectangle,
        
        Dot,
        
        Line
    }

    /**
     * Get the x coordinate of the annotation. This is not necessarily
     * left or right side of the annotation. How the annotation uses this
     * to draw itself is determined by {@link #paint(Graphics2D)}.
     *
     * @return The x coordinate of the annotation.
     */
    int getX();

    /**
     * Set the x coordinate of the annotation. This is not necessarily
     * left or right side of the annotation. How the annotation uses this
     * to draw itself is determined by {@link #paint(Graphics2D)}.
     *
     * @param x The new x coordinate of the annotation.
     */
    void setX(int x);

    /**
     * Get the y coordinate of the annotation. This is not necessarily
     * top or bottom of the annotation. How the annotation uses this
     * to draw itself is determined by {@link #paint(Graphics2D)}.
     *
     * @return The y coordinate of the annotation.
     */
    int getY();

    /**
     * Set the y coordinate of the annotation. This is not necessarily
     * top or bottom of the annotation. How the annotation uses this
     * to draw itself is determined by {@link #paint(Graphics2D)}.
     *
     * @param y The new y coordinate of the annotation.
     */
    void setY(int y);

    /**
     * Get the {@link java.awt.Color} used to draw the annotation.
     *
     * @return The {@link java.awt.Color} used to draw the annotation.
     */
    int getColor();

    /**
     * Set the {@link java.awt.Color} used to draw the annotation.
     *
     * @param color The {@link java.awt.Color} to be used to draw the
     * annotation.
     */
    void setColor(int color);

    /**
     * Paint the annotation using the given {@link java.awt.Graphics2D} object.
     *
     * @param g The {@link java.awt.Graphics2D} object with which to paint
     */
    void paint(Graphics2D g);

    /**
     * Outline the annotation for identification using the given
     * {@link java.awt.Graphics2D} object.
     *
     * @param g The {@link java.awt.Graphics2D} object with which to paint
     */
    void outline(Graphics2D g);

    /**
     * Generate text that will completely describe the annotation. This text
     * will be parsed by the {@link edu.utexas.mgranat.image_annotator
     * .annotations.AnnotationParser#parse(String)} method.
     *
     * @return A text representation of the annotation
     */
    String save();

    /**
     * Generate a description of the annotation that will show up in the
     * {@link edu.utexas.mgranat.image_annotator.gui.AnnotationsPanel}.
     *
     * @return A short description of the annotation
     */
    String display();
}
