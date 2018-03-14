package edu.utexas.mgranat.image_annotator.managers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.utexas.mgranat.image_annotator.ImageAnnotator;

public class SingletonManagerTest {

	@Before
	public void initApp() {
		ImageAnnotator.main(new String[0]);
	}

	@Test
	public void hasImagePanel() {
		assertNotNull(SingletonManager.getImagePanel());
	}

	@Test
	public void hasAnnotationsPanel() {
		assertNotNull(SingletonManager.getAnnotationsPanel());
	}
	
	@Test
	public void hasButtonPanel() {
		assertNotNull(SingletonManager.getButtonPanel());
	}
}
