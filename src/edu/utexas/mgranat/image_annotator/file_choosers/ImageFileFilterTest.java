package edu.utexas.mgranat.image_annotator.file_choosers;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class ImageFileFilterTest {

	@Test
	public void acceptsKnownFiletypes() {
		ImageFileFilter filter = new ImageFileFilter();

		assertTrue(filter.accept(new File("test/filetypes/a.jpg")));
		assertTrue(filter.accept(new File("test/filetypes/b.jpeg")));
		assertTrue(filter.accept(new File("test/filetypes/c.png")));
		assertTrue(filter.accept(new File("test/filetypes/d.bmp")));
		assertTrue(filter.accept(new File("test/filetypes/e.gif")));
	}
	
	@Test
	public void rejectsUnknownFiletypes() {
		ImageFileFilter filter = new ImageFileFilter();
		
		assertFalse(filter.accept(new File("test/filetypes/f.txt")));
		assertFalse(filter.accept(new File("test/filetypes/g.tif")));
	}
	
	@Test
	public void rejectsDirectories() {
		ImageFileFilter filter = new ImageFileFilter();
		
		assertFalse(filter.accept(new File("test/filetypes/h")));
	}
}
