package edu.utexas.mgranat.image_annotator.listeners;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

/**
 * Listener for detecting when the annotation selection changes and updating
 * the corresponding set.
 *
 * @author mgranat
 */
public class AnnotationListSelectionListener implements ListSelectionListener {
    @Override
    public final void valueChanged(final ListSelectionEvent e) {
        for (int i = e.getFirstIndex(); i <= e.getLastIndex(); i++) {
        	if (i < 0 || i >= SingletonManager.getAnnotationsPanel().getModelObjects().size())
        		continue;
        	
            if (SingletonManager.getAnnotationsPanel().getAnnList()
                    .isSelectedIndex(i)) {
                SingletonManager
                        .getAnnotationsPanel()
                        .getSelectedAnnotations()
                        .add(SingletonManager.getAnnotationsPanel()
                                .getModelObjects().get(i));
            } else {
                if (SingletonManager.getAnnotationsPanel().getAnnList()
                        .isSelectionEmpty()) {
                    SingletonManager.getAnnotationsPanel()
                            .getSelectedAnnotations().clear();
                    break;
                } else {
                    SingletonManager
                            .getAnnotationsPanel()
                            .getSelectedAnnotations()
                            .remove(SingletonManager.getAnnotationsPanel()
                                    .getModelObjects().get(i));
                }
            }
        }
        
        SingletonManager.getImagePanel().repaint();
    }
}
