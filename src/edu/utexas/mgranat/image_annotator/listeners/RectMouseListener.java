package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.utexas.mgranat.image_annotator.annotations.RectAnn;
import edu.utexas.mgranat.image_annotator.managers.LoggingManager;
import edu.utexas.mgranat.image_annotator.managers.MessageManager;
import edu.utexas.mgranat.image_annotator.managers.SelectionManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

public class RectMouseListener implements MouseListener, MouseMotionListener {
	/**
	 * Logger for this class.
	 */
	private static Logger m_logger = LoggingManager
			.getLogger(RectMouseListener.class.getName());

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
				new RectAnn((int) dest.getX(), (int) dest.getY(), 0, 0,
						SelectionManager.getSelectedColor().getRGB()));

		// SingletonManager.getImagePanel().addMouseMotionListener(new
		// RectMouseMotionListener());
	}

	@Override
	public void mouseReleased(MouseEvent ev) {
		if (ev.getButton() != MouseEvent.BUTTON1) {
			return;
		}

		if (SingletonManager.getImagePanel().getTempAnn() != null) {
			if (((RectAnn) SingletonManager.getImagePanel().getTempAnn())
					.getWidth() == 0
					&& ((RectAnn) SingletonManager.getImagePanel().getTempAnn())
							.getHeight() == 0) {
				SingletonManager.getImagePanel().setTempAnn(null);
			}
			else if (((RectAnn) SingletonManager.getImagePanel().getTempAnn())
					.getWidth() < 0
					|| ((RectAnn) SingletonManager.getImagePanel().getTempAnn())
							.getHeight() < 0) {
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
				return;
			}
			Point2D src = new Point2D.Double(ev.getX(), ev.getY());
			Point2D dest = new Point2D.Double();
			at.transform(src, dest);

			((RectAnn) SingletonManager.getImagePanel().getTempAnn())
					.setWidth(((int) dest.getX())
							- SingletonManager.getImagePanel().getTempAnn()
									.getX());
			((RectAnn) SingletonManager.getImagePanel().getTempAnn())
					.setHeight(((int) dest.getY())
							- SingletonManager.getImagePanel().getTempAnn()
									.getY());

			SingletonManager.getImagePanel().repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent ev) {
	}
}
