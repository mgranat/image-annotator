package edu.utexas.mgranat.image_annotator.gui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import edu.utexas.mgranat.image_annotator.annotations.IAnnotation;
import edu.utexas.mgranat.image_annotator.graphics.SolidColorIcon;
import edu.utexas.mgranat.image_annotator.listeners.AboutButtonListener;
import edu.utexas.mgranat.image_annotator.listeners.BrowseButtonListener;
import edu.utexas.mgranat.image_annotator.listeners.ColorChooserButtonListener;
import edu.utexas.mgranat.image_annotator.listeners.ExportFolderButtonListener;
import edu.utexas.mgranat.image_annotator.listeners.ExportImageButtonListener;
import edu.utexas.mgranat.image_annotator.listeners.NextButtonListener;
import edu.utexas.mgranat.image_annotator.listeners.PreviousButtonListener;
import edu.utexas.mgranat.image_annotator.listeners.SaveButtonListener;

/**
 * Panel for buttons.
 *
 * @author Max
 */
public class ButtonPanel extends JPanel {
    /**
     * UID for serialization. Not used.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Stores the font size choices presented by the font size combo box.
     */
    private static final Integer[] FONT_SIZE_CHOICES = {1, 2, 3, 4, 5, 6, 7, 8,
        9, 10, 11, 12};

    /**
     * Button for bringing up the color chooser.
     */
    private JButton m_colorChooser;

    /**
     * Combo box for choosing annotation types.
     */
    private JComboBox<IAnnotation.Type> m_annotationChooser;

    /**
     * Combo box for choosing font size.
     */
    private JComboBox<Integer> m_fontSizeChooser;

    /**
     * Check box for selecting a bold font.
     */
    private JCheckBox m_boldCheckBox;

    /**
     * Check box for selecting an italic font.
     */
    private JCheckBox m_italicsCheckBox;

    /**
     * Set up panel and appropriate listeners.
     */
    public ButtonPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton browse = new JButton("Browse...");
        browse.addActionListener(new BrowseButtonListener());
        add(browse);

        JButton previous = new JButton("Previous");
        previous.addActionListener(new PreviousButtonListener());
        add(previous);

        JButton next = new JButton("Next");
        next.addActionListener(new NextButtonListener());
        add(next);

        JButton save = new JButton("Save");
        save.addActionListener(new SaveButtonListener());
        add(save);

        JButton exportImage = new JButton("Export Image");
        exportImage.addActionListener(new ExportImageButtonListener());
        add(exportImage);

        JButton exportFolder = new JButton("Export Folder");
        exportFolder.addActionListener(new ExportFolderButtonListener());
        add(exportFolder);

        DefaultComboBoxModel<IAnnotation.Type> annotationChooserModel =
                new DefaultComboBoxModel<IAnnotation.Type>();
        for (IAnnotation.Type type : IAnnotation.Type.values()) {
            annotationChooserModel.addElement(type);
        }

        m_annotationChooser = new JComboBox<IAnnotation.Type>(
                annotationChooserModel);
        add(m_annotationChooser);

        m_colorChooser = new JButton(new SolidColorIcon(Color.BLACK));
        m_colorChooser.addActionListener(new ColorChooserButtonListener());

        add(m_colorChooser);

        m_fontSizeChooser = new JComboBox<Integer>(FONT_SIZE_CHOICES);
        add(m_fontSizeChooser);

        m_boldCheckBox = new JCheckBox("B");
        add(m_boldCheckBox);

        m_italicsCheckBox = new JCheckBox("I");
        add(m_italicsCheckBox);

        JButton about = new JButton("About");
        about.addActionListener(new AboutButtonListener());
        add(about);
    }

    /**
     * Retrieves the current selected annotation type.
     *
     * @return The selected annotation type
     */
    public final IAnnotation.Type getSelectedAnnotationType() {
        return (IAnnotation.Type) m_annotationChooser.getSelectedItem();
    }

    /**
     * Retrieves the current selected color.
     *
     * @return The current selected color
     */
    public final Color getSelectedColor() {
        return ((SolidColorIcon) m_colorChooser.getIcon()).getColor();
    }

    /**
     * Set the current color for annotations.
     *
     * @param c The new color for annotations
     */
    public final void setColor(final Color c) {
        ((SolidColorIcon) m_colorChooser.getIcon()).setColor(c);
    }

    /**
     * Retrieves the current selected font size.
     *
     * @return The current selected font size
     */
    public final int getSelectedFontSize() {
        return (int) m_fontSizeChooser.getSelectedItem();
    }

    /**
     * Checks if bold is selected.
     *
     * @return True if bold is selected
     */
    public final boolean isBoldSelected() {
        return m_boldCheckBox.isSelected();
    }

    /**
     * Checks if italics is selected.
     *
     * @return True if italics is selected
     */
    public final boolean isItalicsSelected() {
        return m_italicsCheckBox.isSelected();
    }
}
