package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.utexas.mgranat.image_annotator.annotations.LineAnn;
import edu.utexas.mgranat.image_annotator.managers.LoggingManager;
import edu.utexas.mgranat.image_annotator.managers.MessageManager;
import edu.utexas.mgranat.image_annotator.managers.SelectionManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

public class LineMouseListener implements MouseListener, MouseMotionListener {
	/**
	 * Logger for this class.
	 */
	private static Logger m_logger = LoggingManager
			.getLogger(LineMouseListener.class.getName());

	@Override
	public void mouseDragged(MouseEvent ev) {
		if (!((ev.getModifiersEx() & MouseEvent.BUTTON1_DOWN_MASK) == MouseEvent.BUTTON1_DOWN_MASK)) {
			return;
		}

		if (SingletonManager.getImagePanel().getTempAnn() != null) {
			AffineTransform at = SingletonManager.getImagePanel()
					.getTransformClone();
			try {
				at.invert();
			} catch (NoninvertibleTransformException ex) {
				m_logger.log(Level.WARNING,
						"Unexpected error creating annotation", ex);
				MessageManager
						.showMessage("Unexpected error creating annotation");
			}
			Point2D src = new Point2D.Double(ev.getX(), ev.getY());
			Point2D dest = new Point2D.Double();
			at.transform(src, dest);

			((LineAnn) SingletonManager.getImagePanel().getTempAnn()).setX2((int) dest.getX());
			((LineAnn) SingletonManager.getImagePanel().getTempAnn()).setY2((int) dest.getY());

			SingletonManager.getImagePanel().repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent ev) {
	}

	@Override
	public void mouseClicked(MouseEvent ev) {
	}

	@Override
	public void mouseEntered(MouseEvent ev) {
	}

	@Override
	public void mouseExited(MouseEvent ev) {
	}

	@Override
	public void mousePressed(MouseEvent ev) {
		if (ev.getButton() != MouseEvent.BUTTON1) {
			return;
		}

		AffineTransform at = SingletonManager.getImagePanel()
				.getTransformClone();
		try {
			at.invert();
		} catch (NoninvertibleTransformException ex) {
			MessageManager.showMessage("Unexpected error creating annotation");
		}
		Point2D src = new Point2D.Double(ev.getX(), ev.getY());
		Point2D dest = new Point2D.Double();
		at.transform(src, dest);

		SingletonManager.getImagePanel().setTempAnn(
				new LineAnn((int) dest.getX(), (int) dest.getY(), (int) dest.getX(), (int) dest.getY(),
						SelectionManager.getSelectedColor().getRGB()));
	}

	@Override
	public void mouseReleased(MouseEvent ev) {
		if (ev.getButton() != MouseEvent.BUTTON1) {
			return;
		}

		if (SingletonManager.getImagePanel().getTempAnn() != null) {
			if (((LineAnn) SingletonManager.getImagePanel().getTempAnn()).getX() == ((LineAnn) SingletonManager.getImagePanel().getTempAnn()).getX2() 
					&& ((LineAnn) SingletonManager.getImagePanel().getTempAnn()).getY() == ((LineAnn) SingletonManager.getImagePanel().getTempAnn()).getY2()) {
				SingletonManager.getImagePanel().setTempAnn(null);
			}

			if (SingletonManager.getImagePanel().getTempAnn() != null) {
				SingletonManager.getImagePanel().addAnnotationObject(
						SingletonManager.getImagePanel().getTempAnn());
				SingletonManager.getImagePanel().setTempAnn(null);
			}
		}

		SingletonManager.getImagePanel().removeMouseListener(this);
		SingletonManager.getImagePanel().removeMouseMotionListener(this);
	}
}
