package edu.utexas.mgranat.image_annotator.persistence;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import edu.utexas.mgranat.image_annotator.annotations.CircleAnn;
import edu.utexas.mgranat.image_annotator.annotations.DotAnn;
import edu.utexas.mgranat.image_annotator.annotations.LineAnn;
import edu.utexas.mgranat.image_annotator.annotations.RectAnn;
import edu.utexas.mgranat.image_annotator.annotations.IAnnotation;
import edu.utexas.mgranat.image_annotator.annotations.TextAnn;
import edu.utexas.mgranat.image_annotator.managers.LoggingManager;
import edu.utexas.mgranat.image_annotator.managers.MessageManager;

/**
 * Annotation persistence service for XML encoding.
 *
 * @author mgranat
 */
public class XMLAnnotationPersistenceService implements
		IAnnotationPersistenceService {
	/**
	 * Logger for this class.
	 */
	private static Logger m_logger = LoggingManager
			.getLogger(XMLAnnotationPersistenceService.class.getName());

	@Override
	public final void saveAnnotations(final File imageFile,
			final List<IAnnotation> annotations) {
		String filename = makeAnnotationFilename(imageFile.getName());
		File outputFile = new File(imageFile.getParent(), filename);

		if (annotations.size() == 0) {
			if (outputFile.exists()) {
				if (!outputFile.delete()) {
					MessageManager.showMessage("Error deleting "
							+ outputFile.getName());
				}
			}
			return;
		}

		try {
			JAXBContext xmlListContext = JAXBContext.newInstance(
					CircleAnn.class, RectAnn.class, TextAnn.class, DotAnn.class, LineAnn.class,
					XMLList.class);
			Marshaller xmlListMarshaller = xmlListContext.createMarshaller();
			xmlListMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);

			List<IAnnotation> annList = new ArrayList<IAnnotation>();

			for (IAnnotation ann : annotations) {
				annList.add(ann);
			}

			XMLList<IAnnotation> xmlAnnList = new XMLList<IAnnotation>(annList,
					"1");

			xmlListMarshaller.marshal(xmlAnnList, outputFile);
		} catch (JAXBException ex) {
			m_logger.log(Level.WARNING, "Error writing annotations to file", ex);
			MessageManager.showMessage("Error writing annotations to file");
		}
	}

	@Override
	public final List<IAnnotation> loadAnnotations(final File imageFile) {
		String filename = makeAnnotationFilename(imageFile.getName());
		File inputFile = new File(imageFile.getParent(), filename);

		List<IAnnotation> output = new ArrayList<IAnnotation>();

		if (!inputFile.exists()) {
			return output;
		}

		try {
			JAXBContext xmlListContext = JAXBContext.newInstance(
					CircleAnn.class, RectAnn.class, TextAnn.class, DotAnn.class, LineAnn.class,
					XMLList.class);
			Unmarshaller xmlListUnmarshaller = xmlListContext
					.createUnmarshaller();

			@SuppressWarnings("unchecked")
			XMLList<IAnnotation> xmlAnnList = (XMLList<IAnnotation>) xmlListUnmarshaller
					.unmarshal(inputFile);

			List<IAnnotation> annList = xmlAnnList.getList();

			for (IAnnotation ann : annList) {
				output.add(ann);
			}
		} catch (JAXBException ex) {
			m_logger.log(Level.WARNING, "Error writing annotations to file", ex);
			MessageManager.showMessage("Error writing annotations to file");
		}

		return output;
	}

	/**
	 * Make the name of the annotation file corresponding to an image file name.
	 *
	 * @param filename
	 *            The image filename
	 * @return The name of the corresponding annotation file
	 */
	private String makeAnnotationFilename(final String filename) {
		return filename.substring(0, filename.lastIndexOf('.')) + ".ann";
	}
}
