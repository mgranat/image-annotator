package edu.utexas.mgranat.image_annotator.managers;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.io.File;

import org.junit.Before;
import org.junit.Test;

import edu.utexas.mgranat.image_annotator.ImageAnnotator;

public class ImageManagerTest {
	
	@Before
	public void initApp() {
		ImageAnnotator.main(new String[0]);
	}

	@Test
	public void updateImageUpdatesImageCorrectly() {
		File testFile = new File("./test/a.png");
		ImageManager.updateImage(testFile);
		File imageFile = SingletonManager.getImagePanel().getImageFile();
		assertEquals(testFile, imageFile);
	}

	@Test
	public void getDimensionsGetsCorrectDimensions() {
		File testFile = new File("./test/512x512.png");
		ImageManager.updateImage(testFile);
		assertEquals(new Dimension(512, 512), ImageManager.getDimensions());
	}
	
	@Test
	public void nextImageAdvancesCorrectly() {
		File testFile = new File("./test/a.png");
		ImageManager.updateImage(testFile);
		ImageManager.nextImage();
		File nextFile = SingletonManager.getImagePanel().getImageFile();
		assertEquals(new File("./test/b.png"), nextFile);
	}
	
	@Test
	public void previousImageAdvancesCorrectly() {
		File testFile = new File("./test/b.png");
		ImageManager.updateImage(testFile);
		ImageManager.previousImage();
		File nextFile = SingletonManager.getImagePanel().getImageFile();
		assertEquals(new File("./test/a.png"), nextFile);
	}
}
