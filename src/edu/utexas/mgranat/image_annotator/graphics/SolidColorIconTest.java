package edu.utexas.mgranat.image_annotator.graphics;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

public class SolidColorIconTest {
	@Test
	public void colorCanBeSet() {
		SolidColorIcon icon = new SolidColorIcon(Color.black);
		icon.setColor(Color.red);
		assertEquals(Color.red, icon.getColor());
	}
	
	@Test
	public void hasExpectedDimensions() {
		SolidColorIcon icon = new SolidColorIcon(Color.white);
		assertEquals(20, icon.getIconHeight());
		assertEquals(20, icon.getIconWidth());
	}
}
