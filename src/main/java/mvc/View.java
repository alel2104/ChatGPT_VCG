package mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Observable;
import util.Observer;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class serving as the common superclass for any view.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
abstract public class View implements Observable {
    private static final Logger logger = LoggerFactory.getLogger("mvc.view");

    // ////////////////////////////////////////////////////////////////////// //

    private final List<Observer> observers = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    public List<Observer> getObservers() {
        return observers;
    }

    // ////////////////////////////////////////////////////////////////////// //

    public static final String VIEW_OPEN_ADAPTER_LOGS_EVENT = "VIEW_OPEN_ADAPTER_LOGS_EVENT";
    public static final String VIEW_OPEN_SERENADE_LOGS_EVENT = "VIEW_OPEN_SERENADE_LOGS_EVENT";
    public static final String VIEW_CHANGE_GIPPITY_VARIANT_EVENT = "VIEW_CHANGE_GIPPITY_VARIANT_EVENT";

    /**
     * Method for pushing and opening Adapter logs event.
     */
    protected void pushViewOpenAdapterLogs() {
        logger.debug("pushViewOpenAdapterLogs | Triggered.");
        pushEvent(VIEW_OPEN_ADAPTER_LOGS_EVENT);

        try {
            Desktop.getDesktop().open(new File(System.getProperty("user.home") + "/.gptadapter/"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for pushing and opening Serenade logs event.
     */
    protected void pushViewOpenSerenadeLogs() {
        logger.debug("pushViewOpenSerenadeLogs | Triggered.");
        pushEvent(VIEW_OPEN_SERENADE_LOGS_EVENT);

        try {
            Desktop.getDesktop().open(new File(System.getProperty("user.home") + "/.serenade/"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for pushing the change ChatGPT variant event.
     *
     * @param variant The ChatGPT variant.
     */
    protected void pushChangeGippityVariant(Model.GippityVariants variant) {
        logger.debug("pushChangeGippityVariant | Variant: {}", variant.toString());
        pushEvent(VIEW_CHANGE_GIPPITY_VARIANT_EVENT, variant);
    }

    // ////////////////////////////////////////////////////////////////////// //

    /**
     * Method for updating the display of the selected ChatGPT variant.
     *
     * @param variants The ChatGPT variant.
     */
    abstract public void updateGippityVariantDisplay(Model.GippityVariants variants);

    abstract public void updateCurrentStatusMessage(String status);
}
