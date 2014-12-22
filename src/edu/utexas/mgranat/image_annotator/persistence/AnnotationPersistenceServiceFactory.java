package edu.utexas.mgranat.image_annotator.persistence;

/**
 * Factory for annotation persistence service implementations.
 *
 * @author mgranat
 */
public final class AnnotationPersistenceServiceFactory {
    /**
     * Private constructor to prevent instantiation.
     */
    private AnnotationPersistenceServiceFactory() {
    }

    /**
     * Cached annotation persistence service implementation.
     */
    private static IAnnotationPersistenceService m_annotationPersistenceService;

    /**
     * Retrieve the current implementation of the annotation persistence
     * service.
     *
     * @return The current implementation of the annotation persistence
     * service.
     */
    public static IAnnotationPersistenceService
        getAnnotationPersistenceService() {
        if (m_annotationPersistenceService == null) {
            m_annotationPersistenceService =
                    new XMLAnnotationPersistenceService();
        }

        return m_annotationPersistenceService;
    }
}
