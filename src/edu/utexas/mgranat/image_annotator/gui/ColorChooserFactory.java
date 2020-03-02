package edu.utexas.mgranat.image_annotator.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import edu.utexas.mgranat.image_annotator.graphics.SolidColorIcon;

public class ColorChooserFactory
{
	public static JColorChooser createColorChooser()
	{
		JColorChooser colorChooser = new JColorChooser();
		AbstractColorChooserPanel panels[] = { new HSVColorChooserPanel() };
		colorChooser.setChooserPanels(panels);
		
		return colorChooser;
	}
}

class HSVColorChooserPanel extends AbstractColorChooserPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	public HSVColorChooserPanel()
	{
		// FIXME: A better way to do this would be to not treat each color choice
		// as a JButton but to override paintComponent and draw polygons. Then use
		// the same polygons to test if they contain a mouse click. Using no layout
		// manager does not scale but just happens to work with high DPI displays.
		
		// Use no layout manager because we need exact positioning
		setLayout(null);
		
		final int diameterInPixels = 220;
		final float xScaleFactor = 1.2f;
		final float yScaleFactor = 1.2f;
		
		Dimension panelSize = new Dimension(
				(int) (diameterInPixels * xScaleFactor), 
				(int) (diameterInPixels * yScaleFactor));
		setPreferredSize(panelSize);
	}
	
	protected void buildChooser() {
		Insets insets = getInsets();
		
		final int radiusInButtons = 5;
		// FIXME: Magic constant
		final int diameterInPixels = (int) (getPreferredSize().width / 1.2);
		
		final int diameterInButtons = 2 * radiusInButtons - 1;
		final int buttonsOnSide = radiusInButtons;
		final int buttonDiameter = diameterInPixels / diameterInButtons;
		
		int buttonsOnRow = buttonsOnSide;
		int y = buttonDiameter / 2;
		
		final int center = diameterInPixels / 2;
		
		for (int row = 0; row < diameterInButtons; row++)
		{
			int x = (int)((diameterInPixels / 2) - ((double) buttonsOnRow / 2) * buttonDiameter + buttonDiameter / 2);					
			for (int col = 0; col < buttonsOnRow; col++)
			{
				// For color, convert cartesian to polar, normalize radius, create HSB color
				double x_normalized = x - center;
				double y_normalized = y - center;
				double r = Math.sqrt(Math.pow(x_normalized, 2) + Math.pow(y_normalized, 2));
				double r_normalized = r / (diameterInPixels / 2);
				double theta = Math.atan2(y_normalized, x_normalized) + Math.PI;
				
				// Draw button at x, y
				Color color = Color.getHSBColor((float) (theta / (2 * Math.PI)), (float) r_normalized, 1);
				JButton button = new HexagonButton(color);
				//JButton button = new JButton(new SolidColorIcon(color));
				add(button);
				Dimension size = button.getPreferredSize();
				button.setBounds(
						(int)(insets.left + x - (double) buttonDiameter / 2),
						insets.bottom + y,
						size.width,
						size.height);
				
				button.setActionCommand(Integer.toString(color.getRGB()));
				button.addActionListener(this);

				x += buttonDiameter;
			}
			
			y += buttonDiameter;
			
			// There is one more button per row until the center, then it decreases
			if (row < radiusInButtons - 1)
			{
				buttonsOnRow++;
			}
			else
			{
				buttonsOnRow--;
			}
		}
		
	}

	@Override
	public String getDisplayName() {
		return "Choose a color";
	}

	@Override
	public Icon getLargeDisplayIcon() {
		return null;
	}

	@Override
	public Icon getSmallDisplayIcon() {
		return null;
	}

	@Override
	public void updateChooser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Color color = Color.decode(((AbstractButton) e.getSource()).getActionCommand());
		getColorSelectionModel().setSelectedColor(color);
	}
	
	@Override
	public boolean isOptimizedDrawingEnabled()
	{
		return false;
	}
}

class HexagonButton extends JButton
{
	private static final long serialVersionUID = 1L;
	
	private Color m_color;
	
	public HexagonButton(Color color)
	{
		m_color = color;
		
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorderPainted(false);
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(30, 30);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Rectangle bounds = getBounds();
		int diameter = Math.min(bounds.width, bounds.height);
		int radius = diameter / 2;
		Point center = new Point(bounds.width / 2, bounds.height / 2);
		Polygon hexagon = new Polygon();
		for (double theta = Math.PI / 6; theta < 2 * Math.PI + Math.PI / 6; theta += Math.PI / 3)
		{
			hexagon.addPoint(
					(int) Math.round(center.x + radius * Math.cos(theta)),
					(int) Math.round(center.y + radius * Math.sin(theta)));
		}
		
		Color oldColor = g.getColor();
		g.setColor(m_color);
		g.fillPolygon(hexagon);
		g.setColor(Color.BLACK);
		g.drawPolygon(hexagon);
		g.setColor(oldColor);
	}
}
