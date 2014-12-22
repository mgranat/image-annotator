package edu.utexas.mgranat.image_annotator.file_choosers;

import java.io.File;

import javax.swing.JFileChooser;

/**
 * File chooser that shows image files only.
 *
 * @author mgranat
 */
public class ImageFileChooser extends JFileChooser {
    /**
     * UID for serialization. Not used.
     */
    private static final long serialVersionUID = 1;

    /**
     * Constructor that takes a path to show upon opening.
     *
     * @param currentDirectoryPath The path to display upon opening
     */
    public ImageFileChooser(final String currentDirectoryPath) {
        super(currentDirectoryPath);
    }

    @Override
    public final boolean accept(final File f) {
        return f.isDirectory() || new ImageFileFilter().accept(f);
    }
}
