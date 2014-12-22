package edu.utexas.mgranat.image_annotator.annotations;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Annotation type for text.
 *
 * @author Max
 */
@XmlRootElement
public class TextAnn implements IAnnotation {
    /**
     * Default font size.
     */
    public static final int DEFAULT_FONT_SIZE = 12;

    /**
     * Stores the x coordinate of the bottom right corner of the text.
     */
    private int m_xCoord;

    /**
     * Stores the y coordinate of the bottom right corner of the text.
     */
    private int m_yCoord;

    /**
     * Stores the content of the annotation.
     */
    private String m_content;

    /**
     * Stores the color used to draw the text.
     */
    private int m_color;

    /**
     * Stores the font style for this annotation.
     */
    private int m_fontStyle;

    /**
     * Stores the font size of this annotation.
     */
    private int m_fontSize;

    /**
     * Create a text annotation with the specified properties.
     *
     * The only attributes of the font
     * that are stored are the name, style, and size. Other attributes will be
     * discarded.
     *
     * @param x The x coordinate of the bottom right corner of the text
     * @param y The y coordinate of the bottom right corner of the text
     * @param c The color used to draw the text
     * @param fstyle The font style for this annotation
     * @param fsize The font size of this annotation
     * @param s The content of the text
     */
    public TextAnn(final int x, final int y, final int c, final int fstyle,
            final int fsize, final String s) {
        m_xCoord = x;
        m_yCoord = y;
        m_content = s;
        m_color = c;
        m_fontStyle = fstyle;
        m_fontSize = fsize;
    }

    /**
     * No-arg default constructor.
     */
    public TextAnn() {
        this(0, 0, Color.BLACK.getRGB(), Font.PLAIN,
                TextAnn.DEFAULT_FONT_SIZE, "");
    }

    @Override
    public final int getX() {
        return m_xCoord;
    }

    @XmlElement
    @Override
    public final void setX(final int x) {
        m_xCoord = x;
    }

    @Override
    public final int getY() {
        return m_yCoord;
    }

    @XmlElement
    @Override
    public final void setY(final int y) {
        m_yCoord = y;
    }

    /**
     * Retrieve the content of the text.
     *
     * @return The content of the text
     */
    public final String getContent() {
        return m_content;
    }

    /**
     * Set the content of the text.
     *
     * @param content The content of the text
     */
    @XmlElement
    public final void setContent(final String content) {
        m_content = content;
    }

    @Override
    public final int getColor() {
        return m_color;
    }

    @XmlElement
    @Override
    public final void setColor(final int color) {
        m_color = color;
    }

    /**
     * Retrieves the font style for this annotation.
     *
     * @return The font style for this annotation
     */
    public final int getFontStyle() {
        return m_fontStyle;
    }

    /**
     * Set the font style for this annotation.
     *
     * @param fontStyle The font style for this annotation
     */
    @XmlElement
    public final void setFontStyle(final int fontStyle) {
        m_fontStyle = fontStyle;
    }

    /**
     * Retrieves the font size of this annotation.
     *
     * @return The font size of this annotation
     */
    public final int getFontSize() {
        return m_fontSize;
    }

    /**
     * Set the font size of this annotation.
     *
     * @param fontSize The font size of this annotation
     */
    @XmlElement
    public final void setFontSize(final int fontSize) {
        m_fontSize = fontSize;
    }

    @Override
    public final void paint(final Graphics2D g) {
        g.setColor(new Color(m_color));
        g.setFont(new Font("sansserif", m_fontStyle, m_fontSize));
        g.drawString(m_content, m_xCoord, m_yCoord);
    }

    @Override
    public final void outline(final Graphics2D g) {
        g.setColor(Color.YELLOW);

        int width = g.getFontMetrics(
                new Font("sansserif", m_fontStyle, m_fontSize))
                .stringWidth(m_content);
        int height = g.getFontMetrics(
                new Font("sansserif", m_fontStyle, m_fontSize))
                .getMaxAscent();

        g.drawRect(m_xCoord - IAnnotation.OUTLINE_SIZE,
                m_yCoord - height - IAnnotation.OUTLINE_SIZE,
                width + (IAnnotation.OUTLINE_SIZE * 2),
                height + (IAnnotation.OUTLINE_SIZE * 2));
    }

    @Override
    public final String save() {
        return "TextAnn " + m_xCoord + " " + m_yCoord + " " + m_color
                + " " + m_fontStyle + " "
                + m_fontSize + " " + m_content + "\n";
    }

    @Override
    public final String display() {
        return "\"" + m_content + "\"";
    }
    
    @Override
    public final String toString() {
    	return display();
    }
}
