package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;


import edu.utexas.mgranat.image_annotator.annotations.DotAnn;
import edu.utexas.mgranat.image_annotator.managers.ImageManager;
import edu.utexas.mgranat.image_annotator.managers.LoggingManager;
import edu.utexas.mgranat.image_annotator.managers.MessageManager;
import edu.utexas.mgranat.image_annotator.managers.SelectionManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

public class DotMouseListener implements MouseListener {
	/**
	 * Logger for this class.
	 */
	private static Logger m_logger = LoggingManager
			.getLogger(DotMouseListener.class.getName());

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

		SingletonManager.getImagePanel().setTempAnn(new DotAnn((int) dest.getX(), (int) dest.getY(), 0, SelectionManager.getSelectedColor().getRGB()));

		double width = ImageManager.getDimensions().getWidth();
		double height = ImageManager.getDimensions().getHeight();

		double multiplier = (width + height) / 2;

		final double scaler = .002;

		multiplier *= scaler;

		int size = (int) (((double) SelectionManager.getSelectedFontSize()) * multiplier);

		((DotAnn) SingletonManager.getImagePanel().getTempAnn())
				.setRadius(size);
		SingletonManager.getImagePanel().addAnnotationObject(
				SingletonManager.getImagePanel().getTempAnn());
		SingletonManager.getImagePanel().repaint();
		SingletonManager.getImagePanel().setTempAnn(null);
		
		SingletonManager.getImagePanel().removeMouseListener(this);
	}

	@Override
	public void mouseEntered(MouseEvent ev) {
	}

	@Override
	public void mouseExited(MouseEvent ev) {
	}

	@Override
	public void mousePressed(MouseEvent ev) {
	}

	@Override
	public void mouseReleased(MouseEvent ev) {
	}
}
