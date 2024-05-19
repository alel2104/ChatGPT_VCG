package mvc.gui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Class serving as the GUI components factory, used for accessing styled
 * Swing components.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
abstract public class StyledFactory {

    /**
     * Method for creating the styled JPanel component.
     *
     * @return The styled JPanel component.
     */
    public static JPanel getJPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());

        panel.setBackground(GUIConfig.PRIMARY_BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(
                                GUIConfig.MEDIUM_PADDING,
                                GUIConfig.MEDIUM_PADDING,
                                GUIConfig.MEDIUM_PADDING,
                                GUIConfig.MEDIUM_PADDING
                        ),
                        BorderFactory.createLineBorder(
                                GUIConfig.PRIMARY_BORDER_COLOR,
                                GUIConfig.BORDER_THICKNESS
                        )
                ),
                BorderFactory.createEmptyBorder(
                        GUIConfig.MEDIUM_PADDING,
                        GUIConfig.MEDIUM_PADDING,
                        GUIConfig.MEDIUM_PADDING,
                        GUIConfig.MEDIUM_PADDING
                )
        ));

        return panel;
    }

    /**
     * Method for creating the styled JButton component.
     *
     * @return The styled JButton component.
     */
    public static JButton getJButton() {
        JButton button = new JButton();

        button.setBackground(GUIConfig.SECONDARY_BACKGROUND_COLOR);
        button.setForeground(GUIConfig.PRIMARY_TEXT_COLOR);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        GUIConfig.SECONDARY_BORDER_COLOR,
                        GUIConfig.BORDER_THICKNESS),
                BorderFactory.createEmptyBorder(
                        GUIConfig.MEDIUM_PADDING,
                        GUIConfig.MEDIUM_PADDING,
                        GUIConfig.MEDIUM_PADDING,
                        GUIConfig.MEDIUM_PADDING
                )
        ));

        return button;
    }

    /**
     * Method for creating the styled JLabel component.
     *
     * @return The styled JLabel component.
     */
    public static JLabel getLabel() {
        JLabel titleLabel = new JLabel();

        titleLabel.setBackground(GUIConfig.PRIMARY_BACKGROUND_COLOR);
        titleLabel.setForeground(GUIConfig.PRIMARY_TEXT_COLOR);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 14));

        return titleLabel;
    }

    /**
     * Method for creating the styled title JLabel component.
     *
     * @return The styled title JLabel component.
     */
    public static JLabel getTitleLabel() {
        JLabel titleLabel = new JLabel();

        titleLabel.setBackground(GUIConfig.PRIMARY_BACKGROUND_COLOR);
        titleLabel.setForeground(GUIConfig.PRIMARY_TEXT_COLOR);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 28));

        return titleLabel;
    }

    /**
     * Method for creating the styled JTextArea component.
     *
     * @return The styled JTextArea component.
     */
    public static JTextArea getTextArea() {
        JTextArea textArea = new JTextArea();

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBackground(GUIConfig.PRIMARY_BACKGROUND_COLOR);
        textArea.setForeground(GUIConfig.PRIMARY_TEXT_COLOR);
        textArea.setBorder(BorderFactory.createEmptyBorder(
                GUIConfig.MEDIUM_PADDING,
                GUIConfig.MEDIUM_PADDING,
                GUIConfig.MEDIUM_PADDING,
                GUIConfig.MEDIUM_PADDING
        ));
        textArea.setFont(new Font("Roboto", Font.PLAIN, 14));

        return textArea;
    }

    /**
     * Method for creating the styled editable JTextArea component.
     *
     * @return The styled editable JTextArea component.
     */
    public static JTextArea getEditableTextArea() {
        JTextArea textArea = new JTextArea();

        textArea.setEditable(true);
        textArea.setLineWrap(true);
        textArea.setBackground(GUIConfig.SECONDARY_BACKGROUND_COLOR);
        textArea.setForeground(GUIConfig.SECONDARY_TEXT_COLOR);
        textArea.setBorder(BorderFactory.createEmptyBorder(
                GUIConfig.MEDIUM_PADDING,
                GUIConfig.MEDIUM_PADDING,
                GUIConfig.MEDIUM_PADDING,
                GUIConfig.MEDIUM_PADDING
        ));
        textArea.setFont(new Font("Roboto", Font.PLAIN, 14));

        return textArea;
    }

    /**
     * Method for creating the styled JScrollPane component.
     *
     * @param child The JComponent to be placed inside the JScrollPane.
     * @return The styled JScrollPane component.
     */
    public static JScrollPane getJScrollPane(JComponent child) {
        JScrollPane scrollPane = new JScrollPane(child);

        scrollPane.setBackground(GUIConfig.PRIMARY_BACKGROUND_COLOR);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        GUIConfig.SECONDARY_BORDER_COLOR,
                        GUIConfig.BORDER_THICKNESS
                ),
                BorderFactory.createEmptyBorder(
                        GUIConfig.MEDIUM_PADDING,
                        GUIConfig.MEDIUM_PADDING,
                        GUIConfig.MEDIUM_PADDING,
                        GUIConfig.MEDIUM_PADDING
                )
        ));

        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setPreferredSize(new Dimension(0, 0));
        verticalBar.setUnitIncrement(16);

        JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();
        horizontalBar.setPreferredSize(new Dimension(0, 0));
        horizontalBar.setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
    }
}
