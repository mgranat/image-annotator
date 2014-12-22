package edu.utexas.mgranat.image_annotator.persistence;

import java.io.File;
import java.util.List;

import edu.utexas.mgranat.image_annotator.annotations.IAnnotation;

/**
 * Interface for storing annotations to file.
 *
 * @author mgranat
 */
public interface IAnnotationPersistenceService {
    /**
     * Persist a set of annotations corresponding to an image to file.
     *
     * @param imageFile The image corresponding to the set of annotations
     * @param annotations The set of annotations to persist
     */
    void saveAnnotations(File imageFile, List<IAnnotation> annotations);

    /**
     * Load the annotations corresponding to a given image file, if any.
     *
     * @param imageFile The image file to load annotations for
     * @return The set of annotations for the given image, if any
     */
    List<IAnnotation> loadAnnotations(File imageFile);
}
