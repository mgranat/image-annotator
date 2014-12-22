package edu.utexas.mgranat.image_annotator.persistence;

/**
 * Exception thrown when an unrecognized annotation is encountered.
 *
 * @author mgranat
 */
public class UnrecognizedAnnotationException extends Exception {
    /**
     * UID for serialization. Not used.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor that takes a message.
     *
     * @param message The exception message
     */
    public UnrecognizedAnnotationException(final String message) {
        super(message);
    }
}
