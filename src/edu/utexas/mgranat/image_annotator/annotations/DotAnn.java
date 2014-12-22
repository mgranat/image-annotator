package edu.utexas.mgranat.image_annotator.annotations;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DotAnn implements IAnnotation {
	private int m_xCoord;
	private int m_yCoord;
	private int m_color;
	private int m_radius;
	
	public DotAnn(final int x, final int y, final int r, final int c) {
        m_xCoord = x;
        m_yCoord = y;
        m_radius = r;
        m_color = c;
    }
	
	public DotAnn() {
        this(0, 0, 0, Color.BLACK.getRGB());
    }

	@Override
	public int getX() {
		return m_xCoord;
	}

	@XmlElement
	@Override
	public void setX(int x) {
		m_xCoord = x;
	}

	@Override
	public int getY() {
		return m_yCoord;
	}

	@XmlElement
	@Override
	public void setY(int y) {
		m_yCoord = y;
	}

	@Override
	public int getColor() {
		return m_color;
	}

	@XmlElement
	@Override
	public void setColor(int color) {
		m_color = color;
	}
	
	public int getRadius() {
		return m_radius;
	}
	
	@XmlElement
	public void setRadius(int r) {
		m_radius = r;
	}

	@Override
	public void paint(Graphics2D g) {
		g.setColor(new Color(m_color));
		g.fill(new Ellipse2D.Double(m_xCoord - m_radius, m_yCoord - m_radius,
                m_radius * 2, m_radius * 2));
	}

	@Override
	public void outline(Graphics2D g) {
		g.setColor(Color.YELLOW);
        g.draw(new Ellipse2D.Double(
                m_xCoord - m_radius - IAnnotation.OUTLINE_SIZE,
                m_yCoord - m_radius - IAnnotation.OUTLINE_SIZE,
                m_radius * 2 + (IAnnotation.OUTLINE_SIZE * 2),
                m_radius * 2 + (IAnnotation.OUTLINE_SIZE * 2)));
	}

	@Override
	public String save() {
		return "";
	}

	@Override
	public String display() {
		return "Dot";
	}

	@Override
	public String toString() {
		return display();
	}
}
