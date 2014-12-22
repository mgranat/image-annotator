package edu.utexas.mgranat.image_annotator.annotations;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Annotation type for rectangles.
 *
 * @author Max
 */
@XmlRootElement
public class RectAnn implements IAnnotation {
    /**
     * Stores the x coordinate of the top left corner of the rectangle.
     */
    private int m_xCoord;

    /**
     * Stores the y coordinate of the top left corner of the rectangle.
     */
    private int m_yCoord;

    /**
     * Stores the width (along the x axis) of the rectangle.
     */
    private int m_width;

    /**
     * Stores the height (along the y axis) of the rectangle.
     */
    private int m_height;

    /**
     * Stores the color used to draw the rectangle.
     */
    private int m_color;

    /**
     * Create a rectangle annotation with the specified properties.
     *
     * @param x The x coordinate of the top left corner of the rectangle
     * @param y The y coordinate of the top left corner of the rectangle
     * @param w The width (along the x axis) of the rectangle
     * @param h The height (along the y axis) of the rectangle
     * @param color The color used to draw the rectangle
     */
    public RectAnn(final int x, final int y, final int w, final int h,
            final int color) {
        m_xCoord = x;
        m_yCoord = y;
        m_width = w;
        m_height = h;
        m_color = color;
    }

    /**
     * No-arg default constructor.
     */
    public RectAnn() {
        this(0, 0, 0, 0, Color.BLACK.getRGB());
    }

    @Override
    public final int getX() {
        return m_xCoord;
    }

    @XmlElement
    @Override
    public final void setX(final int x) {
        m_xCoord = x;
    }

    @Override
    public final int getY() {
        return m_yCoord;
    }

    @XmlElement
    @Override
    public final void setY(final int y) {
        m_yCoord = y;
    }

    /**
     * Get the width (along the x axis) of the rectangle.
     *
     * @return The width (along the x axis) of the rectangle
     */
    public final int getWidth() {
        return m_width;
    }

    /**
     * Set the width (along the x axis) of the rectangle.
     *
     * @param width The width (along the x axis) of the rectangle
     */
    @XmlElement
    public final void setWidth(final int width) {
        m_width = width;
    }

    /**
     * Get the height (along the y axis) of the rectangle.
     *
     * @return The height (along the y axis) of the rectangle
     */
    public final int getHeight() {
        return m_height;
    }

    /**
     * Set the height (along the y axis) of the rectangle.
     *
     * @param height The height (along the y axis) of the rectangle
     */
    @XmlElement
    public final void setHeight(final int height) {
        m_height = height;
    }

    @Override
    public final int getColor() {
        return m_color;
    }

    @XmlElement
    @Override
    public final void setColor(final int color) {
        m_color = color;
    }

    @Override
    public final void paint(final Graphics2D g) {
        g.setColor(new Color(m_color));
        g.draw(new Rectangle2D.Double(m_xCoord, m_yCoord, m_width, m_height));
    }

    @Override
    public final void outline(final Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.draw(new Rectangle2D.Double(m_xCoord - IAnnotation.OUTLINE_SIZE,
                m_yCoord - IAnnotation.OUTLINE_SIZE,
                m_width + (IAnnotation.OUTLINE_SIZE * 2),
                m_height + (IAnnotation.OUTLINE_SIZE * 2)));
    }

    @Override
    public final String save() {
        return "RectAnn " + m_xCoord + " " + m_yCoord + " " + m_width + " "
                + m_height + " " + m_color + "\n";
    }

    @Override
    public final String display() {
        return "Rectangle";
    }
    
    @Override
    public final String toString() {
    	return display();
    }
}
