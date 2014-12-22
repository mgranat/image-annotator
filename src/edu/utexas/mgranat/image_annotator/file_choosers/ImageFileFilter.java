package edu.utexas.mgranat.image_annotator.file_choosers;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;

/**
 * File filter that accepts image files only.
 *
 * @author mgranat
 */
public class ImageFileFilter implements FileFilter {
    /**
     * Stores the types of files that are accepted.
     */
    private static HashSet<String> filetypes;

    static {
        filetypes = new HashSet<String>();
        filetypes.add(".jpg");
        filetypes.add(".jpeg");
        filetypes.add(".png");
        filetypes.add(".bmp");
        filetypes.add(".gif");
    }

    @Override
    public final boolean accept(final File pathname) {
        if (!pathname.isFile()) {
            return false;
        }

        String pathnameString = pathname.getPath();
        if (pathnameString.contains(".") && !ImageFileFilter.filetypes.contains(pathnameString
                .substring(pathnameString.lastIndexOf('.')).toLowerCase())) {
            return false;
        }

        return true;
    }
}
