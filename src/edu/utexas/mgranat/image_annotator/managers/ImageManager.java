package edu.utexas.mgranat.image_annotator.managers;

import java.awt.Dimension;
import java.io.File;

import edu.utexas.mgranat.image_annotator.file_choosers.ImageFileFilter;

/**
 * Manager for image operations.
 *
 * @author mgranat
 */
public final class ImageManager {
    /**
     * Private constructor to prevent instantiation.
     */
    private ImageManager() {
    }

    /**
     * Change the image file displayed to a new image file.
     *
     * @param newImageFile
     *            The new image file to be displayed
     */
    public static void updateImage(final File newImageFile) {
    	if (null == newImageFile) {
    		return;
    	}
    	
        SingletonManager.getImagePanel().loadImageFile(newImageFile);
        SingletonManager.getImagePanel().repaint();

        SingletonManager.setTitle("Image Annotator - " + newImageFile.getPath().substring(
        		newImageFile.getPath().lastIndexOf(File.separatorChar) + 1));
    }

    /**
     * Retrieves the dimensions of the current image.
     *
     * @return The dimensions of the current image
     */
    public static Dimension getDimensions() {
    	if (SingletonManager.getImagePanel().getImage() == null)
    		return null;
    	
        int width = SingletonManager.getImagePanel().getImage().getWidth();
        int height = SingletonManager.getImagePanel().getImage().getHeight();

        return new Dimension(width, height);
    }

    /**
     * Export a set of images.
     *
     * @param files
     *            The files to be exported
     */
    public static void exportImages(final File[] files) {
        File original = SingletonManager.getImagePanel().getImageFile();

        File exportDir = new File(SingletonManager.getImagePanel()
                .getImageFile().getParent(), "Export");
        exportDir.mkdir();

        final int percentageMultiplier = 100;
        for (int i = 0; i < files.length; i++) {
            SingletonManager.setTitle("Image Annotator - Exporting "
                    + files.length + " files: "
                    + (int) ((((double) i) / ((double) files.length))
                            * percentageMultiplier)
                    + "%");
            File outputFile = new File(exportDir, files[i].getName().substring(
                    0, files[i].getName().lastIndexOf('.'))
                    + "_ann"
                    + files[i].getName().substring(
                            files[i].getName().lastIndexOf('.')));
            SingletonManager.getImagePanel().loadImageFile(files[i]);
            SingletonManager.getImagePanel().export(outputFile);
        }

        updateImage(original);
    }
    
    public static void nextImage() {
    	if (!SingletonManager.getImagePanel().hasImage()) {
            return;
        }

        File[] filesInDir = SingletonManager.getImagePanel().getImageFile()
                .getParentFile().listFiles(new ImageFileFilter());

        int i = 0;
        while (i < filesInDir.length
                && !SingletonManager.getImagePanel().getImageFile().getPath()
                        .equals(filesInDir[i].getPath())) {
            i++;
        }

        int outputFileIndex = 0;
        if (i == filesInDir.length - 1) {
            outputFileIndex = 0;
        } else {
            outputFileIndex = i + 1;
        }

        updateImage(filesInDir[outputFileIndex]);
    }
    
    public static void previousImage() {
    	if (!SingletonManager.getImagePanel().hasImage()) {
            return;
        }

        File[] filesInDir = SingletonManager.getImagePanel().getImageFile()
                .getParentFile().listFiles(new ImageFileFilter());

        int i = 0;
        while (i < filesInDir.length
                && !SingletonManager.getImagePanel().getImageFile().getPath()
                        .equals(filesInDir[i].getPath())) {
            i++;
        }

        int outputFileIndex = 0;
        if (i == 0) {
            outputFileIndex = filesInDir.length - 1;
        } else {
            outputFileIndex = i - 1;
        }

        updateImage(filesInDir[outputFileIndex]);
    }
}
