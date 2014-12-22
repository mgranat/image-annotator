package edu.utexas.mgranat.image_annotator.persistence;

import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Versioned list of elements that can be marshalled to XML.
 *
 * @author mgranat
 *
 * @param <V> The type of the list
 */
@XmlRootElement
public class XMLList<V> {
    /**
     * The list to be marshalled.
     */
    private List<V> m_list;

    /**
     * The version of the encoding.
     */
    private String m_version;

    /**
     * Creates a versioned list of objects to be marshalled to XML.
     *
     * @param list The list of objects to be marshalled
     * @param version The version of this encoding
     */
    public XMLList(final List<V> list, final String version) {
        m_list = list;
        m_version = version;
    }

    /**
     * No-argument default constructor.
     */
    public XMLList() {
        this(null, "");
    }

    /**
     * Retrieves the list of objects to be marshalled.
     *
     * @return The list of objects to be marshalled
     */
    public final List<V> getList() {
        return m_list;
    }

    /**
     * Set the list of objects to be marshalled.
     *
     * @param list The list of objects to be marshalled
     */
    @XmlAnyElement(lax = true)
    public final void setList(final List<V> list) {
        m_list = list;
    }

    /**
     * Retrieves the version of this encoding.
     *
     * @return The version of this encoding
     */
    public final String getVersion() {
        return m_version;
    }

    /**
     * Sets the version of this encoding.
     *
     * @param version The version of this encoding
     */
    @XmlElement
    public final void setVersion(final String version) {
        m_version = version;
    }
}
