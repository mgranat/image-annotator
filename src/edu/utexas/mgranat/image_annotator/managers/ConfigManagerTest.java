package edu.utexas.mgranat.image_annotator.managers;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.utexas.mgranat.image_annotator.ImageAnnotator;

public class ConfigManagerTest {
	@Before
	public void initApp() {
		ImageAnnotator.main(new String[0]);
		/*
		File oldConfigFile = ConfigManager.getConfigFile();
		String configFileName = oldConfigFile.getName();
		File configFileBackup = new File(oldConfigFile.getParentFile(), configFileName + ".bak");
		try {
			Files.copy(oldConfigFile.toPath(), configFileBackup.toPath(), REPLACE_EXISTING);
		} catch (IOException ex) {
			fail("Error backup up config file: " + ex.getMessage());
		}
		*/
	}
	
	@Test
	public void hasProperties() {
		assertNotNull(ConfigManager.getProperty("annotations.outline.spacing"));
		assertNotNull(ConfigManager.getProperty("annotations.thickness"));
		assertNotNull(ConfigManager.getProperty("window.annotations_panel.size"));
		assertNotNull(ConfigManager.getProperty("window.width"));
		assertNotNull(ConfigManager.getProperty("window.height"));
		assertNotNull(ConfigManager.getProperty("window.zoom.min"));
		assertNotNull(ConfigManager.getProperty("window.zoom.max"));
		assertNotNull(ConfigManager.getProperty("window.zoom.multiplier"));
	}
	
	/*
	@After
	public void cleanup() {
		File configFile = ConfigManager.getConfigFile();
		String configFileName = configFile.getName();
		File configFileBackup = new File(configFile.getParentFile(), configFileName + ".bak");
		try {
			Files.copy(configFileBackup.toPath(), configFile.toPath(), REPLACE_EXISTING);
			// Files.delete(configFileBackup.toPath());
		} catch (IOException ex) {
			fail("Error restoring backup config file: " + ex.getMessage());
		}
	}
	*/
}
