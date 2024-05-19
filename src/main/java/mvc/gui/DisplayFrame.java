package mvc.gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

/**
 * Class serving as the main application window.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
public class DisplayFrame extends JFrame {
    private JPanel header;
    private JPanel footer;
    private JTextArea currentStatus;

    /**
     * Constructor to initialize the main application window.
     *
     * @param mainPanel The main panel of the application window.
     */
    public DisplayFrame(JPanel mainPanel) {
        super();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setMinimumSize(GUIConfig.WINDOW_SIZE);

        this.initializeHeader();
        this.add(this.header, BorderLayout.PAGE_START);

        this.initializeFooter();
        this.add(this.footer, BorderLayout.PAGE_END);

        this.add(mainPanel);

        this.setVisible(true);
    }

    private void initializeHeaderStatusPanel() {
        JPanel headerStatus = StyledFactory.getJPanel();
        headerStatus.setBorder(null);

        JLabel statusTitle = StyledFactory.getTitleLabel();
        statusTitle.setText("Status: ");
        this.currentStatus = StyledFactory.getTextArea();
        //this.currentStatus.setFont(new Font("Roboto", Font.PLAIN, 12));
        this.currentStatus.setBackground(GUIConfig.SECONDARY_BACKGROUND_COLOR);
        this.currentStatus.setForeground(GUIConfig.SECONDARY_TEXT_COLOR);
        this.currentStatus.setColumns(20);

        headerStatus.add(statusTitle, BorderLayout.WEST);
        headerStatus.add(currentStatus, BorderLayout.EAST);

        this.header.add(headerStatus, BorderLayout.EAST);
    }

    /**
     * Method for initializing the main window header.
     */
    private void initializeHeader() {
        this.header = StyledFactory.getJPanel();

        JLabel title = StyledFactory.getTitleLabel();
        title.setText("GPT Adapter");
        this.header.add(title, BorderLayout.WEST);

        this.initializeHeaderStatusPanel();
    }

    /**
     * Method for initializing the main window footer.
     */
    private void initializeFooter() {
        this.footer = StyledFactory.getJPanel();

        JLabel text = StyledFactory.getLabel();
        text.setText("Albin Eliasson (alel2104) & Martin K. Herkules (makr1906)");
        this.footer.add(text);
    }

    public JTextArea getCurrentStatus() {
        return this.currentStatus;
    }

    public void setCurrentStatus(String status) {
        this.currentStatus.setText(status);
    }
}
