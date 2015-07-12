package edu.utexas.mgranat.image_annotator.annotations;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Annotation type for circles.
 *
 * @author Max
 */
@XmlRootElement
public class CircleAnn implements IAnnotation {
	private static final int DEFAULT_THICKNESS = 20;
	
    /**
     * Stores the x coordinate of the top left corner of the circle.
     */
    private int m_xCoord;

    /**
     * Stores the y coordinate of the top left corner of the circle.
     */
    private int m_yCoord;

    /**
     * Stores the radius of the circle.
     */
    private int m_radius;
    
    private int m_thickness;

    /**
     * Stores the color used to draw the circle.
     */
    private int m_color;

    /**
     * Create a circle annotation with the specified properties.
     *
     * @param x
     *            The x coordinate of the upper left corner of the circle
     * @param y
     *            The y coordinate of the upper left corner of the circle
     * @param r
     *            The radius of the circle
     * @param c
     *            The color used to draw the circle
     */
    public CircleAnn(final int x, final int y, final int r, final int c, final int t) {
        m_xCoord = x;
        m_yCoord = y;
        m_radius = r;
        m_color = c;
        m_thickness = t;
    }

    /**
     * No-argument default constructor.
     */
    public CircleAnn() {
        this(0, 0, 0, Color.BLACK.getRGB(), DEFAULT_THICKNESS);
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
     * Retrieve the radius of the circle.
     *
     * @return The radius of the circle
     */
    public final int getRadius() {
        return m_radius;
    }

    /**
     * Set the radius of the circle.
     *
     * @param r
     *            The radius of the circle
     */
    @XmlElement
    public final void setRadius(final int r) {
        m_radius = r;
    }

    @Override
    public final int getColor() {
        return m_color;
    }

    @XmlElement
    @Override
    public final void setColor(final int c) {
        m_color = c;
    }
    
    public final int getThickness() {
    	return m_thickness;
    }
    
    @XmlElement
    public final void setThickness(final int t) {
    	m_thickness = t;
    }

    @Override
    public final void paint(final Graphics2D g) {
    	Stroke previousStroke = g.getStroke();
    	g.setStroke(new BasicStroke(m_thickness));
        g.setColor(new Color(m_color));
        g.draw(new Ellipse2D.Double(m_xCoord - m_radius, m_yCoord - m_radius,
                m_radius * 2, m_radius * 2));
        g.setStroke(previousStroke);
    }

    @Override
    public final void outline(final Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.draw(new Ellipse2D.Double(
                m_xCoord - m_radius - IAnnotation.OUTLINE_SIZE,
                m_yCoord - m_radius - IAnnotation.OUTLINE_SIZE,
                m_radius * 2 + (IAnnotation.OUTLINE_SIZE * 2),
                m_radius * 2 + (IAnnotation.OUTLINE_SIZE * 2)));
    }

    @Override
    public final String save() {
        return "CircleAnn " + m_xCoord + " " + m_yCoord + " " + m_radius + " "
                + m_color + "\n";
    }

    @Override
    public final String display() {
        return "Circle";
    }
    
    @Override
    public final String toString() {
    	return display();
    }
}
