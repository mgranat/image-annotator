package edu.utexas.mgranat.image_annotator.gui;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import edu.utexas.mgranat.image_annotator.annotations.IAnnotation;
import edu.utexas.mgranat.image_annotator.annotations.TextAnn;
import edu.utexas.mgranat.image_annotator.listeners.ImagePanelMouseListener;
import edu.utexas.mgranat.image_annotator.listeners.ZoomAndPanListener;
import edu.utexas.mgranat.image_annotator.managers.ConfigManager;
import edu.utexas.mgranat.image_annotator.managers.ImageManager;
import edu.utexas.mgranat.image_annotator.managers.LoggingManager;
import edu.utexas.mgranat.image_annotator.managers.MessageManager;
import edu.utexas.mgranat.image_annotator.managers.SelectionManager;
import edu.utexas.mgranat.image_annotator.managers.SingletonManager;
import edu.utexas.mgranat.image_annotator
    .persistence.AnnotationPersistenceServiceFactory;

/**
 * JPanel for displaying a zoomable and pannable image. Zoom and pan integration
 * based on files found here: https://community.oracle.com/message/5358787
 *
 * @author mgranat
 */
public class ImagePanel extends JPanel {
    /**
     * UID for serialization. Not used.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Logger for this class.
     */
    private static Logger m_logger =
            LoggingManager.getLogger(ImagePanel.class.getName());

    /**
     * Stores if the panel has been initialized. The panel needs to be
     * initialized whenever a new image is loaded.
     */
    private boolean m_init = false;

    /**
     * Stores whether the image has been altered and thus, there have been
     * changes to the annotations.
     */
    private boolean m_altered;

    /**
     * Stores the listener that provides zoom and pan functionality.
     */
    private ZoomAndPanListener m_zp;

    /**
     * Stores the image to be displayed.
     */
    private BufferedImage m_image;

    /**
     * Stores the location of the image.
     */
    private File m_imageFile;

    /**
     * Stores an annotation temporarily while it is being drawn.
     */
    private IAnnotation m_tempAnn;

    /**
     * Stores the set of annotations on the image.
     */
    private List<IAnnotation> m_annotations;

    /**
     * Constructor sets up listeners.
     */
    public ImagePanel() {
        m_zp = new ZoomAndPanListener(this);
        addMouseListener(m_zp);
        addMouseMotionListener(m_zp);
        addMouseWheelListener(m_zp);

        // annotation listener
        addMouseListener(new ImagePanelMouseListener());
        
        // RMB listener
        addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent ev) {
				if (ev.getModifiers() == MouseEvent.BUTTON3_MASK) {
					ImageManager.nextImage();
				}
			}
        });
        
        String nextActionName = "next image";
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), nextActionName);
        getActionMap().put(nextActionName, new AbstractAction(){
			/**
			 * Default UID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent ev) {
				ImageManager.nextImage();
			}
        });
        
        String previousActionName = "previous image";
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), previousActionName);
        getActionMap().put(previousActionName, new AbstractAction(){
			/**
			 * Default UID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent ev) {
				ImageManager.previousImage();
			}
        });

        m_tempAnn = null;
        m_annotations = new ArrayList<IAnnotation>();
        m_altered = false;
    }

    /**
     * Checks to see if the image panel currently has an image loaded.
     *
     * @return True if an image is loaded, false otherwise
     */
    public final boolean hasImage() {
        return !(m_image == null);
    }

    /**
     * Retrieves the current loaded image file.
     *
     * @return The current loaded image file
     */
    public final File getImageFile() {
        return m_imageFile;
    }

    /**
     * Retrieves the current loaded image.
     *
     * @return The current loaded image
     */
    public final BufferedImage getImage() {
        return m_image;
    }

    /**
     * Load a new image file.
     *
     * @param newImageFile
     *            The image file to be loaded
     */
    public final void loadImageFile(final File newImageFile) {
        saveAnnotations();

        try {
            m_image = ImageIO.read(newImageFile);
        } catch (IOException ex) {
            m_logger.log(Level.WARNING, "Error opening \""
                    + newImageFile.getPath() + "\"", ex);
            MessageManager.showMessage("Error opening \""
                    + newImageFile.getPath() + "\"");
            return;
        }

        m_imageFile = newImageFile;
        m_init = false;
        m_annotations.clear();
        m_altered = false;

        loadAnnotations();
        SingletonManager.getAnnotationsPanel().clearSelectedAnnotations();
    }

    /**
     * Save the current set of annotations to file.
     */
    public final void saveAnnotations() {
        if (m_imageFile == null /*|| !m_altered*/) {
            return;
        }

        AnnotationPersistenceServiceFactory.getAnnotationPersistenceService()
            .saveAnnotations(m_imageFile, m_annotations);
    }

    /**
     * Retrieve the current set of annotations on the image.
     *
     * @return The current set of annotations on the image
     */
    public final List<IAnnotation> getAnnotations() {
        return m_annotations;
    }

    /**
     * Add an annotation to the current image.
     *
     * @param ann
     *            The annotation to add
     */
    public final void loadAnnotation(final IAnnotation ann) {
        m_annotations.add(ann);
    }

    /**
     * Checks to see if the image has been altered.
     *
     * @return True if the image has been altered, false otherwise
     */
    public final boolean isAltered() {
        return m_altered;
    }

    /**
     * Set whether or not the image has been altered since the last save.
     *
     * @param status
     *            The new status of the image
     */
    public final void setAltered(final boolean status) {
        m_altered = status;
    }

    /**
     *
     * @return asdf
     */
    public final IAnnotation getTempAnn() {
        return m_tempAnn;
    }

    /**
     * Set the temporary annotation.
     *
     * @param ann
     *            The new temporary annotation
     */
    public final void setTempAnn(final IAnnotation ann) {
        m_tempAnn = ann;
    }

    /**
     * Export the current image to the provided file.
     *
     * @param outputFile
     *            The file to be exported to
     */
    public final void export(final File outputFile) {
        m_logger.log(Level.INFO, "Exporting " + m_imageFile.getName());

        /*
         * if the photo has no annotations, it is exported as an exact copy of
         * the input file
         */
        if (m_annotations.size() == 0) {
            copy(outputFile);
            return;
        }

        JPanel outputPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(final Graphics g1) {
                super.paintComponent(g1);

                Graphics2D g = (Graphics2D) g1;

                g.drawImage(m_image, 0, 0, new JLabel(
                        "Image observer - should not be visible"));

                paintAnnotations(g);
            }
        };
        outputPanel.setSize(m_image.getWidth(), m_image.getHeight());

        BufferedImage outputImage = new BufferedImage(m_image.getWidth(),
                m_image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = outputImage.createGraphics();
        outputPanel.paint(g);

        try {
            /*
             * higher quality output based on info found here:
             * http://www.universalwebservices
             * .net/web-programming-resources/java
             * /adjust-jpeg-image-compression-quality-when-saving-images-in-java
             */
            Iterator<ImageWriter> iter = ImageIO
                    .getImageWritersByFormatName("jpeg");
            ImageWriter writer = (ImageWriter) iter.next();

            ImageWriteParam iwp = writer.getDefaultWriteParam();
            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            iwp.setCompressionQuality(1);

            FileImageOutputStream output =
                    new FileImageOutputStream(outputFile);
            writer.setOutput(output);
            IIOImage imageForOutput = new IIOImage(outputImage, null, null);
            writer.write(null, imageForOutput, iwp);
            writer.dispose();
            output.close();
        } catch (IOException ex) {
            m_logger.log(Level.WARNING, "Error exporting \""
                    + m_imageFile.getName() + "\"", ex);
            MessageManager.showMessage("Error exporting \""
                    + m_imageFile.getName() + "\"");
        }
    }

    @Override
    public final void paintComponent(final Graphics g1) {
        super.paintComponent(g1);
        Graphics2D g = (Graphics2D) g1;

        if (!m_init) {
            // Initialize the viewport by moving the origin to the center of the
            // window
            m_init = true;

            Dimension d = getSize();
            int xc = d.width / 2;
            int yc = d.height / 2;
            g.translate(xc, yc);

            // resize the viewport if the image is too big
            if (m_image != null) {
                double xScale = ((double) getSize().width) / m_image.getWidth();
                double yScale = ((double) getSize().height)
                        / m_image.getHeight();

                if (xScale > yScale) {
                    g.scale(yScale, yScale);
                } else {
                    g.scale(xScale, xScale);
                }
            }

            if (m_image != null) {
                // move viewport to center of image
                xc = -m_image.getWidth() / 2;
                yc = -m_image.getHeight() / 2;
                g.translate(xc, yc);

                // scale zoom bounds to reflect image size
                // (inverse) determines how much of an effect the image size on
                // the zoom bounds
                final int dimensionWeight = 100;
                int largerDimension;
                if (m_image.getWidth() < m_image.getHeight()) {
                    largerDimension = m_image.getHeight();
                } else {
                    largerDimension = m_image.getWidth();
                }
                final int lowerZoomBound = -10;
                final int upperZoomBound = 5;
                m_zp.setZoomBounds(
                        lowerZoomBound - largerDimension / dimensionWeight,
                        upperZoomBound + largerDimension / dimensionWeight);
            } else {
                m_zp.resetZoomBounds();
            }

            // Save the viewport to be updated by the ZoomAndPanListener
            m_zp.setCoordTransform(g.getTransform());
            m_zp.setZoomLevel(0);
        } else {
            // Restore the viewport after it was updated by the
            // ZoomAndPanListener
            g.transform(m_zp.getCoordTransform());
        }

        if (m_image == null) {
            g.drawString("No image loaded.", 0, 0);
        } else {
            g.drawImage(m_image, 0, 0, null);
        }

        paintAnnotations(g);

        if (m_tempAnn != null && !(m_tempAnn instanceof TextAnn)) {
            m_tempAnn.paint(g);
        }

        for (IAnnotation ann : SelectionManager.getSelectedAnnotations()) {
            ann.outline(g);
        }
    }

    /**
     * Paint the annotations using the provided Graphics2D object.
     *
     * @param g The Graphics2D object to be used to paint.
     */
    private void paintAnnotations(final Graphics2D g) {
        if (m_image != null) {
            double avgDim = (m_image.getHeight() + m_image.getWidth()) / 2;
            double multiplier =
                    Double.parseDouble(
                            ConfigManager.getProperty("annotations.thickness"));
            double strokeSize = multiplier * avgDim;
            g.setStroke(new BasicStroke((float) strokeSize));
        }

        for (IAnnotation ann : m_annotations) {
            ann.paint(g);
        }
    }

    /**
     * Add an annotation object to the current image.
     *
     * @param ann
     *            The annotation to be added
     */
    public final void addAnnotationObject(final IAnnotation ann) {
        m_annotations.add(ann);
        m_altered = true;
        SingletonManager.getAnnotationsPanel().updateSelection();
    }

    /**
     * Retrieve a copy of the current transform.
     *
     * @return A copy of the current transform
     */
    public final AffineTransform getTransformClone() {
        return (AffineTransform) m_zp.getCoordTransform().clone();
    }

    /**
     * Load annotations from the annotations file associated with this image.
     */
    private void loadAnnotations() {
        m_annotations = AnnotationPersistenceServiceFactory
                .getAnnotationPersistenceService()
                    .loadAnnotations(m_imageFile);

        SingletonManager.getAnnotationsPanel().updateSelection();
    }

    /**
     * Copy the current photo to the specified file. Used if the photo has no
     * annotations and should exported as an exact copy of the input file.
     *
     * @param dst
     *            The destination file
     */
    private void copy(final File dst) {
        final int bufsize = 1024;

        try {
            InputStream in = new FileInputStream(m_imageFile);
            OutputStream out = new FileOutputStream(dst);

            // Transfer bytes from in to out
            byte[] buf = new byte[bufsize];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException ex) {
            m_logger.log(Level.WARNING, "Error exporting \""
                    + m_imageFile.getName() + "\"", ex);
            MessageManager.showMessage("Error exporting \""
                    + m_imageFile.getName() + "\"");
        }
    }
}
