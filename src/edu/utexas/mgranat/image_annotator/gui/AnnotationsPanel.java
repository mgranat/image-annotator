package edu.utexas.mgranat.image_annotator.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import edu.utexas.mgranat.image_annotator.annotations.IAnnotation;
import edu.utexas.mgranat.image_annotator.listeners
    .AnnotationListSelectionListener;
import edu.utexas.mgranat.image_annotator.listeners.DeleteButtonListener;
import edu.utexas.mgranat.image_annotator.listeners.RepaintImagePanelListener;
import edu.utexas.mgranat.image_annotator.managers.ConfigManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

/**
 * Panel for displaying text representations of and deleting annotations.
 *
 * @author mgranat
 */
public class AnnotationsPanel extends JPanel {
    /**
     * UID for serialization. Not used.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Stores the default size of the panel.
     */
    private static final int DEFAULT_SIZE =
            Integer.parseInt(
                    ConfigManager.getProperty("window.annotations_panel.size"));

    /**
     * List of annotations displayed in the panel.
     */
    private JList<IAnnotation> m_annList;

    /**
     * List model for strings for display. Kept in sync with the list model for
     * annotation objects.
     */
    //private DefaultListModel<String> m_model;

    /**
     * List model for annotation objects. Kept in sync with the list model for
     * display strings.
     */
    private DefaultListModel<IAnnotation> m_listModel;

    /**
     * Stores the default dimension of the panel.
     */
    private Dimension m_defaultSize = new Dimension(DEFAULT_SIZE, DEFAULT_SIZE);

    /**
     * Stores the set of selected annotations.
     */
    private HashSet<IAnnotation> m_selectedAnnotations;
    
    private JCheckBox m_displayAnnotationsCheckbox;

    /**
     * Set up the panel and appropriate listeners.
     */
    public AnnotationsPanel() {
        //m_model = new DefaultListModel<String>();
        m_listModel = new DefaultListModel<IAnnotation>();
        m_annList = new JList<IAnnotation>(m_listModel);
        m_annList.setLayoutOrientation(JList.VERTICAL);
        m_annList.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        m_selectedAnnotations = new HashSet<IAnnotation>();
        m_annList.addListSelectionListener(
                new AnnotationListSelectionListener());

        JScrollPane scrollPane = new JScrollPane(m_annList);
        scrollPane.setMinimumSize(m_defaultSize);
        scrollPane.setPreferredSize(m_defaultSize);
        scrollPane.setMaximumSize(m_defaultSize);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        
        JPanel annotationsButtonsPanel = new JPanel();
        annotationsButtonsPanel.setLayout(new GridLayout(0, 1));
        
        m_displayAnnotationsCheckbox = new JCheckBox("Display");
        m_displayAnnotationsCheckbox.setSelected(true);
        m_displayAnnotationsCheckbox.addActionListener(new RepaintImagePanelListener());
        annotationsButtonsPanel.add(m_displayAnnotationsCheckbox);

        JButton delete = new JButton("Delete");
        delete.addActionListener(new DeleteButtonListener());
        annotationsButtonsPanel.add(delete);

        add(annotationsButtonsPanel, BorderLayout.NORTH);
        
        String deleteActionName = "delete selection";
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DELETE"), deleteActionName);
        getActionMap().put(deleteActionName, new AbstractAction(){
			/**
			 * Default UID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent ev) {
				if (!getSelectedAnnotations().isEmpty()) {
					delete(SingletonManager.getAnnotationsPanel().getAnnList()
		                            .getSelectedIndices());
				}
			}
        });
    }

    /**
     * Retrieve the set of selected annotations.
     *
     * @return The set of selected annotations
     */
    public final HashSet<IAnnotation> getSelectedAnnotations() {
        return m_selectedAnnotations;
    }

    /**
     * Clear the set of selected annotations.
     */
    public final void clearSelectedAnnotations() {
        m_selectedAnnotations.clear();
    }

    /**
     * Retrieve the JList of annotations.
     *
     * @return The JList of annotations
     */
    public final JList<IAnnotation> getAnnList() {
        return m_annList;
    }

    /**
     * Retrieve the list model of annotation objects.
     *
     * @return The list model of annotation objects
     */
    public final DefaultListModel<IAnnotation> getModelObjects() {
        return m_listModel;
    }
    
    public final boolean getDisplayAnnotationsChecked() {
    	return m_displayAnnotationsCheckbox.isSelected();
    }

    /**
     * Delete a set of annotations from the image.
     *
     * @param indices
     *            Array of indices of annotations to delete. Must be in ascending order
     */
    public final void delete(final int[] indices) {
        for (int i = indices.length - 1; i >= 0; i--) {
        	IAnnotation removed = m_listModel.remove(indices[i]);
        	SingletonManager.getImagePanel().getAnnotations().remove(removed);
        	m_selectedAnnotations.remove(removed);
        }
        
        SingletonManager.getImagePanel().repaint();
        SingletonManager.getImagePanel().setAltered(true);
        SingletonManager.getImagePanel().saveAnnotations();
        //updateSelection();
        
        revalidate();
        repaint();
        
    	/*
    	// remove from model
        // remove from modelObjects
        // remove from annotations
        ArrayList<IAnnotation> toRemove = new ArrayList<IAnnotation>();
        for (int i : indices) {
            toRemove.add(m_listModel.get(i));
        }

        Set<IAnnotation> annotations = SingletonManager.getImagePanel()
                .getAnnotations();
        for (IAnnotation a : toRemove) {
            if (!annotations.remove(a)) {
                throw new IllegalStateException("Element " + a.save()
                        + " not found.");
            }
            m_selectedAnnotations.remove(a);
        }

        m_listModel.clear();
        m_model.clear();

        SingletonManager.getImagePanel().repaint();
        SingletonManager.getImagePanel().setAltered(true);
        SingletonManager.getImagePanel().saveAnnotations();
        updateSelection();
        */
    }

    /**
     * Update the annotations panel when changes are made.
     */
    public final void updateSelection() {
        //m_model.clear();
        m_listModel.clear();

        List<IAnnotation> annotations = SingletonManager.getImagePanel()
                .getAnnotations();
        for (IAnnotation ann : annotations) {
            //m_model.addElement(ann.display());
            m_listModel.addElement(ann);
        }

        revalidate();
        repaint();
    }
}
