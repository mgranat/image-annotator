package edu.utexas.mgranat.image_annotator.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.utexas.mgranat.image_annotator.annotations.CircleAnn;
import edu.utexas.mgranat.image_annotator.annotations.IAnnotation;
import edu.utexas.mgranat.image_annotator.annotations.RectAnn;
import edu.utexas.mgranat.image_annotator.annotations.TextAnn;
import edu.utexas.mgranat.image_annotator.managers.LoggingManager;
import edu.utexas.mgranat.image_annotator.managers.MessageManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;

/**
 * Annotation persistence service that saves annotations as plaintext.
 *
 * @author mgranat
 */
public class PlaintextAnnotationPersistenceService
    implements IAnnotationPersistenceService {
    /**
     * Logger for this class.
     */
    private static Logger m_logger = LoggingManager.getLogger(
                    PlaintextAnnotationPersistenceService.class.getName());

    @Override
    public final void saveAnnotations(final File imageFile,
            final List<IAnnotation> annotations) {
        String filename = makeAnnotationFilename(imageFile.getName());
        File outputFile = new File(imageFile.getParent(), filename);

        if (annotations.size() == 0) {
            if (!outputFile.delete()) {
                MessageManager.showMessage("Error deleting "
                                + outputFile.getName());
            }
        }

        StringBuilder output = new StringBuilder();
        for (IAnnotation ann : annotations) {
            output.append(ann.save());
        }

        try {
            PrintWriter outputWriter = new PrintWriter(new FileWriter(
                    outputFile));
            outputWriter.print(output.toString());
            outputWriter.close();
        } catch (IOException ex) {
            m_logger.log(Level.WARNING, "Error saving annotations", ex);
            MessageManager.showMessage("Error saving annotations");
            return;
        }

        SingletonManager.getImagePanel().setAltered(false);
    }

    @Override
    public final List<IAnnotation> loadAnnotations(final File imageFile) {
        final String filename = makeAnnotationFilename(imageFile.getName());

        File[] files = imageFile.getParentFile().listFiles(new FileFilter() {
            @Override
            public boolean accept(final File file) {
                return file.getName().equals(filename);
            }
        });

        List<IAnnotation> output = new ArrayList<IAnnotation>();

        if (files.length == 0) {
            SingletonManager.getAnnotationsPanel().updateSelection();
            return output;
        }

        File input = files[0];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input));

            while (reader.ready()) {
                output.add(parse(reader.readLine()));
            }

            reader.close();
        } catch (IOException ex) {
            m_logger.log(Level.WARNING, "Error loading annotations from file",
                    ex);
            MessageManager.showMessage("Error loading annotations from file");
        } catch (UnrecognizedAnnotationException ex) {
            m_logger.log(Level.WARNING, "Unrecognized annotation", ex);
            MessageManager.showMessage("Unrecognized annotation");
        }

        return output;
    }

    /**
     * Make the name of the annotation file corresponding to an image file name.
     *
     * @param filename The image filename
     * @return The name of the corresponding annotation file
     */
    private String makeAnnotationFilename(final String filename) {
        return filename.substring(0, filename.lastIndexOf('.')) + ".ann";
    }

    /**
     * Parses the textual representation of an annotation.
     *
     * @param s Textual representation of an annotation.
     * @return An IAnnotation object of the correct type
     * @throws UnrecognizedAnnotationException If an unrecognized annotation
     * is encountered
     */
    private IAnnotation parse(final String s)
            throws UnrecognizedAnnotationException {
        String[] arr = s.split(" ");

        IAnnotation output;

        int i = 0;
        switch (arr[i++]) {
        case "TextAnn":
            output = new TextAnn(Integer.parseInt(arr[i++]),
                    Integer.parseInt(arr[i++]),
                            Integer.parseInt(arr[i++]),
                    Integer.parseInt(arr[i++]), Integer
                            .parseInt(arr[i++]), "");

            String content = "";
            for (; i < arr.length; i++) {
                content += arr[i] + " ";
            }
            content.trim();
            ((TextAnn) output).setContent(content);
            break;
        case "CircleAnn":
            output = new CircleAnn(Integer.parseInt(arr[i++]),
                    Integer.parseInt(arr[i++]), Integer.parseInt(arr[i++]),
                    Integer.parseInt(arr[i++]));
            break;
        case "RectAnn":
            output = new RectAnn(Integer.parseInt(arr[i++]),
                    Integer.parseInt(arr[i++]), Integer.parseInt(arr[i++]),
                    Integer.parseInt(arr[i++]),
                            Integer.parseInt(arr[i++]));
            break;
        default:
            throw new UnrecognizedAnnotationException(
                    "Unrecognized annotation: " + s);
        }

        return output;
    }
}
