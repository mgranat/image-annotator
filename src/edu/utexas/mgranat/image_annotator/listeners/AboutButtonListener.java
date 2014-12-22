package edu.utexas.mgranat.image_annotator.listeners;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import edu.utexas.mgranat.image_annotator.managers.MessageManager;

/**
 * Listener for the "about" button. Provides version information and
 * changelogs.
 *
 * @author mgranat
 */
public class AboutButtonListener implements ActionListener {
    /**
     * List of changelogs.
     */
    private static ArrayList<Changelog> m_changelogs;

    /**
     * Default width of the about scrollpane.
     */
    private static final int DEFAULT_WIDTH = 500;

    /**
     * Default height of the about scrollpane.
     */
    private static final int DEFAULT_HEIGHT = 400;

    @Override
    public final void actionPerformed(final ActionEvent e) {
        JTextArea textArea = new JTextArea(generateChangelog());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        scrollPane.setPreferredSize(
                new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        MessageManager.showMessage(scrollPane);
    }

    /**
     * Generate the text to display the changelog.
     *
     * @return The changelog in String form
     */
    private String generateChangelog() {
        if (m_changelogs == null) {
            m_changelogs = buildChangelogs();
        }

        StringBuilder output = new StringBuilder();

        final String separator = "\n\n\n";

        for (int i = 0; i < m_changelogs.size(); i++) {
            output.append(m_changelogs.get(i).toString());

            if (i != m_changelogs.size() - 1) {
                output.append(separator);
            }
        }

        return output.toString();
    }

    /**
     * Object for encapsulating changelog text and version information.
     *
     * @author mgranat
     */
    private class Changelog {
        /**
         * String representation of the version this changelog will describe.
         */
        private String m_version;

        /**
         * Changelog text.
         */
        private String m_text;

        /**
         * Set the version this changelog will describe.
         *
         * @param version The version this changelog will describe
         */
        public void setVersion(final String version) {
            m_version = version;
        }

        /**
         * Set the text of the changelog.
         *
         * @param text The text of the changelog
         */
        public void setText(final String text) {
            m_text = text;
        }

        @Override
        public String toString() {
            return m_version + "\n\n" + m_text;
        }
    }

    /**
     * Builds the actual changelogs including their text and adds them to an
     * ArrayList.
     *
     * @return An ArrayList of the changelogs
     */
    private ArrayList<Changelog> buildChangelogs() {
        ArrayList<Changelog> changelogs = new ArrayList<Changelog>();

        /* Add new changelogs here */

        Changelog zeroPointOne = new Changelog();
        zeroPointOne.setVersion("0.1");
        zeroPointOne.setText(
                "New Features\n"
                + "- Added configuration options\n"
                + "- Added logging\n"
                + "- Added about button and changelogs\n"
                + "Changes\n"
                + "- Single mode operation\n"
                + "- Annotation size scales relative to photo size\n"
                + "- Browse now opens the directory of the last image\n"
                + "- Image now defaults to fill the screen\n"
                + "- Removed info panel at the bottom of the screen\n"
                + "- Changed look and feel\n"
                + "- Standardized error messages\n"
                + "Bugfixes\n"
                + "- Fixed portions of images being drawn incorrectly when "
                + "combo boxes are triggered\n"
                + "- Fixed text jumping on first click\n");
        changelogs.add(zeroPointOne);

        Changelog initial = new Changelog();
        initial.setVersion("Initial");
        initial.setText(
                "- Zoomable and pannable image panel\n"
                + "- Panel where annotations can be selected and deleted\n"
                + "- Two mode operation: \"View\" and \"Annotate\"\n"
                + "- Circle, Rectangle, and Text annotations\n"
                + "- Export by image and by folder\n"
                + "- Color, text size, and bold and italics options\n");
        changelogs.add(initial);

        return changelogs;
    }
}
