package mvc;

import mvc.gui.DisplayFrame;
import mvc.gui.GUIConfig;
import mvc.gui.StyledFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

/**
 * The app view component containing a concrete implementation of a view.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
public class AppView extends View {
    private static final Logger logger = LoggerFactory.getLogger("mvc.view");

    // ////////////////////////////////////////////////////////////////////// //

    private final DisplayFrame frame;
    private final JPanel mainPanel;

    private JPanel gippityButtonPanel;
    private Map<Model.GippityVariants, JButton> gippityButtons;

    private JPanel logButtonPanel;

    /**
     * Constructor to initialize the GUI view components.
     */
    public AppView() {
        logger.debug("Constructor() | Initializing.");

        this.mainPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        this.mainPanel.setBackground(Color.RED);

        initializeGippityButtonPanel();
        this.mainPanel.add(this.gippityButtonPanel);
        initializeLogButtonPanel();
        this.mainPanel.add(this.logButtonPanel);

        this.frame = new DisplayFrame(this.mainPanel);
    }

    /**
     * Method for initializing the ChatGPT GUI button panel.
     */
    private void initializeGippityButtonPanel() {
        this.gippityButtonPanel = StyledFactory.getJPanel();

        JLabel title = StyledFactory.getTitleLabel();
        title.setText("GPT Variant Selection:");
        this.gippityButtonPanel.add(title, BorderLayout.PAGE_START);

        JTextArea description = StyledFactory.getTextArea();
        description.setText("Lorem ipsum");
        this.gippityButtonPanel.add(description, BorderLayout.CENTER);

        this.gippityButtons = new HashMap<>();

        JPanel buttonContainer = new JPanel(new GridLayout(4, 1, GUIConfig.MEDIUM_PADDING, GUIConfig.MEDIUM_PADDING));
        buttonContainer.setOpaque(false);

        JButton gippityFourKeywordButton = StyledFactory.getJButton();
        this.gippityButtons.put(Model.GippityVariants.FourKeyword, gippityFourKeywordButton);
        gippityFourKeywordButton.setText("Select GPT 4 Keyword.");
        gippityFourKeywordButton.addActionListener(event -> pushChangeGippityVariant(Model.GippityVariants.FourKeyword));
        gippityFourKeywordButton.setEnabled(false);
        buttonContainer.add(gippityFourKeywordButton);

        JButton gippityFourDirect = StyledFactory.getJButton();
        this.gippityButtons.put(Model.GippityVariants.FourDirect, gippityFourDirect);
        gippityFourDirect.setText("Select GPT 4 Direct.");
        gippityFourDirect.addActionListener(event -> pushChangeGippityVariant(Model.GippityVariants.FourDirect));
        buttonContainer.add(gippityFourDirect);

        JButton gippityThreeFiveKeywordButton = StyledFactory.getJButton();
        this.gippityButtons.put(Model.GippityVariants.ThreeFiveKeyword, gippityThreeFiveKeywordButton);
        gippityThreeFiveKeywordButton.setText("Select GPT 3.5 Keyword.");
        gippityThreeFiveKeywordButton.addActionListener(event -> pushChangeGippityVariant(Model.GippityVariants.ThreeFiveKeyword));
        buttonContainer.add(gippityThreeFiveKeywordButton);

        JButton gippityThreeFiveDirectButton = StyledFactory.getJButton();
        this.gippityButtons.put(Model.GippityVariants.ThreeFiveDirect, gippityThreeFiveDirectButton);
        gippityThreeFiveDirectButton.setText("Select GPT 3.5 Direct.");
        gippityThreeFiveDirectButton.addActionListener(event -> pushChangeGippityVariant(Model.GippityVariants.ThreeFiveDirect));
        buttonContainer.add(gippityThreeFiveDirectButton);

        this.gippityButtonPanel.add(buttonContainer, BorderLayout.PAGE_END);
    }

    /**
     * Method for initializing the log GUI button panel.
     */
    private void initializeLogButtonPanel() {
        this.logButtonPanel = StyledFactory.getJPanel();

        JLabel title = StyledFactory.getTitleLabel();
        title.setText("Logging:");
        this.logButtonPanel.add(title, BorderLayout.PAGE_START);

        JTextArea description = StyledFactory.getTextArea();
        description.setText("Lorem ipsum");
        this.logButtonPanel.add(description, BorderLayout.CENTER);

        JPanel buttonContainer = new JPanel(new GridLayout(2, 1, GUIConfig.MEDIUM_PADDING, GUIConfig.MEDIUM_PADDING));
        buttonContainer.setOpaque(false);

        JButton adapterLogsButton = StyledFactory.getJButton();
        adapterLogsButton.setText("Open adapter logs file location.");
        adapterLogsButton.addActionListener(event -> this.pushViewOpenAdapterLogs());
        buttonContainer.add(adapterLogsButton);

        JButton serenadeLogsButton = StyledFactory.getJButton();
        serenadeLogsButton.setText("Open Serenade logs file location.");
        serenadeLogsButton.addActionListener(event -> this.pushViewOpenSerenadeLogs());
        buttonContainer.add(serenadeLogsButton);

        this.logButtonPanel.add(buttonContainer, BorderLayout.PAGE_END);
    }

    // ////////////////////////////////////////////////////////////////////// //

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGippityVariantDisplay(Model.GippityVariants variant) {
        this.gippityButtons.forEach((v, b) -> b.setEnabled(v != variant));
        updateCurrentStatusMessage("GPT variant changed!");
    }

    @Override
    public void updateCurrentStatusMessage(String status) {
        frame.setCurrentStatus(status);
    }
}
