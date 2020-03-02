package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;

import edu.utexas.mgranat.image_annotator.gui.ColorChooserFactory;
import edu.utexas.mgranat.image_annotator.managers.SelectionManager;

public class ColorChooserButtonListener implements ActionListener {
    @Override
    public final void actionPerformed(final ActionEvent e) {
    	// TODO make previously selected color selected
    	// Color previousColor = SelectionManager.getSelectedColor();
    	
    	JFrame colorChooserFrame = new JFrame();
    	JColorChooser colorChooser = ColorChooserFactory.createColorChooser();
    	final String title = "Choose a color";
    	final boolean modal = true;
    	ActionListener okButtonListener = new ColorChooserDialogOkButtonListener(colorChooser);
    	ActionListener cancelButtonListener = null;
        JDialog colorChooserDialog = JColorChooser.createDialog(colorChooserFrame, title, modal, colorChooser, okButtonListener, cancelButtonListener);
        colorChooserFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        colorChooserDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        colorChooserDialog.setVisible(true);        
    }
}

class ColorChooserDialogOkButtonListener implements ActionListener
{
	private JColorChooser m_colorChooser;
	
	public ColorChooserDialogOkButtonListener(JColorChooser colorChooser)
	{
		m_colorChooser = colorChooser;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Color chosenColor = m_colorChooser.getColor();
		
		if (chosenColor != null)
		{
			SelectionManager.setColor(chosenColor);
		}
	}
	
}
