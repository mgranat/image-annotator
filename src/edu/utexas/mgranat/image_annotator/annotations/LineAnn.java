package edu.utexas.mgranat.image_annotator.annotations;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LineAnn implements IAnnotation {
	private int m_x1;
	private int m_y1;
	private int m_x2;
	private int m_y2;
	private int m_color;
	
	public LineAnn(int x1, int y1, int x2, int y2, int color) {
		m_x1 = x1;
		m_y1 = y1;
		m_x2 = x2;
		m_y2 = y2;
		m_color = color;
	}
	
	public LineAnn() {
		this(0, 0, 0, 0, Color.BLACK.getRGB());
	}

	@Override
	public int getX() {
		return m_x1;
	}

	@XmlElement
	@Override
	public void setX(int x) {
		m_x1 = x;
	}

	@Override
	public int getY() {
		return m_y1;
	}

	@XmlElement
	@Override
	public void setY(int y) {
		m_y1 = y;
	}
	
	public int getX2() {
		return m_x2;
	}
	
	@XmlElement
	public void setX2(int x) {
		m_x2 = x;
	}
	
	public int getY2() {
		return m_y2;
	}
	
	@XmlElement
	public void setY2(int y) {
		m_y2 = y;
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

	@Override
	public void paint(Graphics2D g) {
		g.setColor(new Color(m_color));
		g.draw(new Line2D.Double(m_x1, m_y1, m_x2, m_y2));
	}

	@Override
	public void outline(Graphics2D g) {
		g.setColor(Color.YELLOW);
		g.draw(new Line2D.Double(m_x1, m_y1, m_x2, m_y2).getBounds2D());
	}

	@Override
	public String save() {
		return "";
	}

	@Override
	public String display() {
		return "Line";
	}

	@Override
	public String toString() {
		return display();
	}
}
