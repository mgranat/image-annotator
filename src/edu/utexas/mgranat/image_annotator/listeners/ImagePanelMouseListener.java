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
        case Text:
        	TextMouseListener textMouseListener = new TextMouseListener();
        	SingletonManager.getImagePanel().addMouseListener(textMouseListener);
        	textMouseListener.mouseClicked(ev);
            break;
        case Circle:
            break;
        case Rectangle:
            break;
        case Dot:
        	DotMouseListener dotMouseListener = new DotMouseListener();
        	SingletonManager.getImagePanel().addMouseListener(dotMouseListener);
        	dotMouseListener.mouseClicked(ev);
        	break;
        case Line:
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
        case Text:
            break;
        case Circle:
            CircleMouseListener circleMouseListener = new CircleMouseListener();
            circleMouseListener.mousePressed(ev);
            SingletonManager.getImagePanel().addMouseListener(circleMouseListener);
            SingletonManager.getImagePanel().addMouseMotionListener(circleMouseListener);
            break;
        case Rectangle:
        	RectMouseListener rectMouseListener = new RectMouseListener();
        	rectMouseListener.mousePressed(ev);
            SingletonManager.getImagePanel().addMouseListener(rectMouseListener);
            SingletonManager.getImagePanel().addMouseMotionListener(rectMouseListener);
            break;
        case Dot:
        	break;
        case Line:
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
