package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import edu.utexas.mgranat.image_annotator.annotations.IAnnotation;
import edu.utexas.mgranat.image_annotator.managers.SelectionManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

/**
 * Listener for drawing annotations.
 *
 * @author mgranat
 */
public class ImagePanelMouseListener implements MouseListener {
    @Override
    public final void mouseClicked(final MouseEvent ev) {
        if (ev.getButton() != MouseEvent.BUTTON1) {
            return;
        }

        IAnnotation.Type annotationType = SelectionManager
                .getSelectedAnnotationType();

        switch (annotationType) {
        case TEXT:
        	TextMouseListener textMouseListener = new TextMouseListener();
        	SingletonManager.getImagePanel().addMouseListener(textMouseListener);
        	textMouseListener.mouseClicked(ev);
            break;
        case CIRCLE:
            break;
        case RECTANGLE:
            break;
        case DOT:
        	DotMouseListener dotMouseListener = new DotMouseListener();
        	SingletonManager.getImagePanel().addMouseListener(dotMouseListener);
        	dotMouseListener.mouseClicked(ev);
        	break;
        case LINE:
        	break;
        default:
            break;
        }
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    @Override
    public final void mousePressed(final MouseEvent ev) {
        if (ev.getButton() != MouseEvent.BUTTON1) {
            return;
        }

        IAnnotation.Type annotationType = SelectionManager
                .getSelectedAnnotationType();

        switch (annotationType) {
        case TEXT:
            break;
        case CIRCLE:
            CircleMouseListener circleMouseListener = new CircleMouseListener();
            circleMouseListener.mousePressed(ev);
            SingletonManager.getImagePanel().addMouseListener(circleMouseListener);
            SingletonManager.getImagePanel().addMouseMotionListener(circleMouseListener);
            break;
        case RECTANGLE:
        	RectMouseListener rectMouseListener = new RectMouseListener();
        	rectMouseListener.mousePressed(ev);
            SingletonManager.getImagePanel().addMouseListener(rectMouseListener);
            SingletonManager.getImagePanel().addMouseMotionListener(rectMouseListener);
            break;
        case DOT:
        	break;
        case LINE:
        	LineMouseListener lineMouseListener = new LineMouseListener();
        	lineMouseListener.mousePressed(ev);
        	SingletonManager.getImagePanel().addMouseListener(lineMouseListener);
            SingletonManager.getImagePanel().addMouseMotionListener(lineMouseListener);
        	break;
        default :
            break;
        }
    }

    @Override
    public final void mouseReleased(final MouseEvent e) {
    }
}
