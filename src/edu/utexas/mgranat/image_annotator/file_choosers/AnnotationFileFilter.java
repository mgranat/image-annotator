package edu.utexas.mgranat.image_annotator.file_choosers;

import java.io.File;
import java.io.FileFilter;

public class AnnotationFileFilter implements FileFilter {
	@Override
	public boolean accept(File pathname) {
		if (!pathname.isFile()) {
            return false;
        }

        String pathnameString = pathname.getPath();
		if (pathnameString.contains(".") && pathnameString.substring(pathnameString.lastIndexOf('.')).toLowerCase().equals(".ann")) {
            return true;
        }

        return false;
	}
}
