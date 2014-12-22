package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.utexas.mgranat.image_annotator.managers.ConfigManager;
import edu.utexas.mgranat.image_annotator.managers.LoggingManager;
import edu.utexas.mgranat.image_annotator.managers.MessageManager;

//FIXME zoom bounds should not change while one image is open

/**
 * Listener for zoom and pan commands. Based on files found here:
 * https://community.oracle.com/message/5358787
 *
 * @author mgranat
 */
public class ZoomAndPanListener implements MouseListener, MouseMotionListener,
        MouseWheelListener {
    /**
     * Logger for this class.
     */
    private static Logger m_logger =
            LoggingManager.getLogger(ZoomAndPanListener.class.getName());

    /**
     * Stores the default minimum zoom level.
     */
    public static final int DEFAULT_MIN_ZOOM_LEVEL =
            Integer.parseInt(ConfigManager.getProperty("window.zoom.min"));

    /**
     * Stores the default maximum zoom level.
     */
    public static final int DEFAULT_MAX_ZOOM_LEVEL =
            Integer.parseInt(ConfigManager.getProperty("window.zoom.max"));

    /**
     * Stores the default zoom multiplication factor.
     */
    public static final double DEFAULT_ZOOM_MULTIPLICATION_FACTOR =
            Double.parseDouble(
                    ConfigManager.getProperty("window.zoom.multiplier"));

    /**
     * Stores the target component.
     */
    private Component m_targetComponent;

    /**
     * Stores the current zoom level.
     */
    private int m_zoomLevel = 0;

    /**
     * Stores the current minimum zoom level.
     */
    private int m_minZoomLevel = DEFAULT_MIN_ZOOM_LEVEL;

    /**
     * Stores the current maxmimum zoom level.
     */
    private int m_maxZoomLevel = DEFAULT_MAX_ZOOM_LEVEL;

    /**
     * Stores the current zoom multiplication factor.
     */
    private double m_zoomMultiplicationFactor =
            DEFAULT_ZOOM_MULTIPLICATION_FACTOR;

    /**
     * Stores the starting point for a drag event.
     */
    private Point m_dragStartScreen;

    /**
     * Stores the ending point for a drag event.
     */
    private Point m_dragEndScreen;

    /**
     * Stores the current transform.
     */
    private AffineTransform m_coordTransform = new AffineTransform();

    /**
     * Construct a listener on the target component.
     *
     * @param target
     *            The target component
     */
    public ZoomAndPanListener(final Component target) {
        m_targetComponent = target;
    }

    /**
     * Construct a listener with extra parameters.
     *
     * @param target
     *            The target component
     * @param minZoom
     *            The minimum zoom level
     * @param maxZoom
     *            The maximum zoom level
     * @param zoomMultFactor
     *            The zoom multiplication factor
     */
    public ZoomAndPanListener(final Component target, final int minZoom,
            final int maxZoom, final double zoomMultFactor) {
        m_targetComponent = target;
        m_minZoomLevel = minZoom;
        m_maxZoomLevel = maxZoom;
        m_zoomMultiplicationFactor = zoomMultFactor;
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    @Override
    public final void mousePressed(final MouseEvent e) {
        if (!(e.getButton() == MouseEvent.BUTTON2)) {
            return;
        }

        m_dragStartScreen = e.getPoint();
        m_dragEndScreen = null;
    }

    @Override
    public final void mouseReleased(final MouseEvent e) {
        if (!(e.getButton() == MouseEvent.BUTTON2)) {
            return;
        }

        moveCamera(e);
    }

    @Override
    public final void mouseWheelMoved(final MouseWheelEvent e) {
        zoomCamera(e);
    }

    @Override
    public final void mouseDragged(final MouseEvent e) {
        if ((e.getModifiersEx() & MouseEvent.BUTTON2_DOWN_MASK) == 0) {
            return;
        }

        moveCamera(e);
    }

    /**
     * Retrieve the current zoom level.
     *
     * @return The current zoom level
     */
    public final int getZoomLevel() {
        return m_zoomLevel;
    }

    /**
     * Set the current zoom level to the desired amount.
     *
     * @param zoom
     *            The desired zoom level
     */
    public final void setZoomLevel(final int zoom) {
        m_zoomLevel = zoom;
    }

    /**
     * Set the zoom bounds to the specified minimum and maximum.
     *
     * @param min
     *            The desired minimum zoom level
     * @param max
     *            The desired maxmium zoom level
     */
    public final void setZoomBounds(final int min, final int max) {
        m_minZoomLevel = min;
        m_maxZoomLevel = max;
    }

    /**
     * Reset the zoom bounds to default.
     */
    public final void resetZoomBounds() {
        m_minZoomLevel = DEFAULT_MIN_ZOOM_LEVEL;
        m_maxZoomLevel = DEFAULT_MAX_ZOOM_LEVEL;
    }

    /**
     * Get the current transform on the image.
     *
     * @return The current transform
     */
    public final AffineTransform getCoordTransform() {
        return m_coordTransform;
    }

    /**
     * Set the current transform.
     *
     * @param newTransform
     *            The desired transform
     */
    public final void setCoordTransform(final AffineTransform newTransform) {
        m_coordTransform = newTransform;
    }

    /**
     * Move the camera according to the provided mouse event.
     *
     * @param event
     *            The mouse event that the camera movement will be based on
     */
    private void moveCamera(final MouseEvent event) {
        try {
            m_dragEndScreen = event.getPoint();
            Point2D.Float dragStart = transformPoint(m_dragStartScreen);
            Point2D.Float dragEnd = transformPoint(m_dragEndScreen);
            double dx = dragEnd.getX() - dragStart.getX();
            double dy = dragEnd.getY() - dragStart.getY();
            m_coordTransform.translate(dx, dy);
            m_dragStartScreen = m_dragEndScreen;
            m_dragEndScreen = null;
            m_targetComponent.repaint();
        } catch (NoninvertibleTransformException ex) {
            m_logger.log(Level.WARNING, "Unexpected error moving camera", ex);
            MessageManager.showMessage("Unexpected error moving camera");
        }
    }

    /**
     * Zoom the camera based on the provided mouse event.
     *
     * @param event
     *            The mouse event that the camera zoom will be based on
     */
    private void zoomCamera(final MouseWheelEvent event) {
        try {
            int wheelRotation = event.getWheelRotation();
            Point p = event.getPoint();
            if (wheelRotation > 0) {
                if (m_zoomLevel < m_maxZoomLevel) {
                    m_zoomLevel++;
                    Point2D p1 = transformPoint(p);
                    m_coordTransform.scale(1 / m_zoomMultiplicationFactor,
                            1 / m_zoomMultiplicationFactor);
                    Point2D p2 = transformPoint(p);
                    m_coordTransform.translate(p2.getX() - p1.getX(), p2.getY()
                            - p1.getY());
                    m_targetComponent.repaint();
                }
            } else {
                if (m_zoomLevel > m_minZoomLevel) {
                    m_zoomLevel--;
                    Point2D p1 = transformPoint(p);
                    m_coordTransform.scale(m_zoomMultiplicationFactor,
                            m_zoomMultiplicationFactor);
                    Point2D p2 = transformPoint(p);
                    m_coordTransform.translate(p2.getX() - p1.getX(), p2.getY()
                            - p1.getY());
                    m_targetComponent.repaint();
                }
            }
        } catch (NoninvertibleTransformException ex) {
            m_logger.log(Level.WARNING, "Unexpected error zooming camera", ex);
            MessageManager.showMessage("Unexpected error zooming camera");
        }
    }

    /**
     * Transform a point according to the current transform.
     *
     * @param inputPoint
     *            The point to transform
     * @return The transformed point
     * @throws NoninvertibleTransformException
     *             If the transform is non-invertible
     */
    private Point2D.Float transformPoint(final Point inputPoint)
            throws NoninvertibleTransformException {
        AffineTransform inverse = m_coordTransform.createInverse();
        Point2D.Float outputPoint = new Point2D.Float();
        inverse.transform(inputPoint, outputPoint);
        return outputPoint;
    }
}
