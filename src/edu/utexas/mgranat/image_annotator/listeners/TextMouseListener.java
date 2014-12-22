package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import edu.utexas.mgranat.image_annotator.annotations.TextAnn;
import edu.utexas.mgranat.image_annotator.managers.ImageManager;
import edu.utexas.mgranat.image_annotator.managers.LoggingManager;
import edu.utexas.mgranat.image_annotator.managers.MessageManager;
import edu.utexas.mgranat.image_annotator.managers.SelectionManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

public class TextMouseListener implements MouseListener {
	/**
	 * Logger for this class.
	 */
	private static Logger m_logger = LoggingManager
			.getLogger(TextMouseListener.class.getName());

	@Override
	public void mouseClicked(MouseEvent ev) {
		AffineTransform at = SingletonManager.getImagePanel()
				.getTransformClone();
		try {
			at.invert();
		} catch (NoninvertibleTransformException ex) {
			m_logger.log(Level.WARNING, "Unexpected error creating annotation",
					ex);
			MessageManager.showMessage("Unexpected error creating annotation");
		}
		Point2D src = new Point2D.Double(ev.getX(), ev.getY());
		Point2D dest = new Point2D.Double();
		at.transform(src, dest);

		SingletonManager.getImagePanel().setTempAnn(
				new TextAnn((int) dest.getX(), (int) dest.getY(),
						SelectionManager.getSelectedColor().getRGB(),
						Font.PLAIN, TextAnn.DEFAULT_FONT_SIZE, ""));
		String input = JOptionPane.showInputDialog("Enter text:");
		if (input == null || input.equals("")) {
			SingletonManager.getImagePanel().setTempAnn(null);
			return;
		}
		((TextAnn) SingletonManager.getImagePanel().getTempAnn())
				.setContent(input);
		int bold = 0, italics = 0;
		if (SelectionManager.isBoldSelected()) {
			bold = Font.BOLD;
		}
		if (SelectionManager.isItalicsSelected()) {
			italics = Font.ITALIC;
		}
		int style = bold | italics;

		double width = ImageManager.getDimensions().getWidth();
		double height = ImageManager.getDimensions().getHeight();

		double multiplier = (width + height) / 2;

		final double scaler = .01;

		multiplier *= scaler;

		int size = (int) (((double) SelectionManager.getSelectedFontSize()) * multiplier);

		((TextAnn) SingletonManager.getImagePanel().getTempAnn())
				.setFontStyle(style);
		((TextAnn) SingletonManager.getImagePanel().getTempAnn())
				.setFontSize(size);
		SingletonManager.getImagePanel().addAnnotationObject(
				SingletonManager.getImagePanel().getTempAnn());
		SingletonManager.getImagePanel().repaint();
		SingletonManager.getImagePanel().setTempAnn(null);
		
		SingletonManager.getImagePanel().removeMouseListener(this);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
