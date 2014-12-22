package edu.utexas.mgranat.image_annotator.graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * Icon that is just a solid color. Based on code found here:
 * http://www.codebeach.com/2007/06/creating-dynamic-icons-in-java.html
 *
 * @author Max
 */
public class SolidColorIcon implements Icon {
    /**
     * Stores the height of the icon.
     */
    private static final int HEIGHT = 20;

    /**
     * Stores the width of the icon.
     */
    private static final int WIDTH = 20;

    /**
     * Stores the color of the icon.
     */
    private Color m_color;

    /**
     * Create an icon of the specified color.
     *
     * @param color The color of the icon
     */
    public SolidColorIcon(final Color color) {
        m_color = color;
    }

    /**
     * Set the color of the icon.
     *
     * @param color The color of the icon
     */
    public final void setColor(final Color color) {
        m_color = color;
    }

    /**
     * Get the color of the icon.
     *
     * @return The color of the icon
     */
    public final Color getColor() {
        return m_color;
    }

    @Override
    public final int getIconHeight() {
        return HEIGHT;
    }

    @Override
    public final int getIconWidth() {
        return WIDTH;
    }

    @Override
    public final void paintIcon(final Component component, final Graphics g,
            final int x, final int y) {
        g.setColor(m_color);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

}
