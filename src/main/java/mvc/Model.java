package mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Observable;
import util.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Class serving as the common superclass for any model.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
abstract public class Model implements Observable {
    private static final Logger logger = LoggerFactory.getLogger("mvc.model");

    // ////////////////////////////////////////////////////////////////////// //

    /**
     * Enum of the ChatGPT variants, version 4 and 3.5 with direct code generation
     * and code keyword generation.
     */
    public enum GippityVariants {
        ThreeFiveKeyword,
        ThreeFiveDirect,
        FourKeyword,
        FourDirect
    }

    /**
     * Getter for the current ChatGPT variant.
     *
     * @return The current ChatGPT variant.
     */
    abstract public GippityVariants getGippityVariant();

    /**
     * Setter for the current ChatGPT variant.
     *
     * @param variant The current ChatGPT variant.
     */
    abstract public void setGippityVariant(GippityVariants variant);

    /**
     * Method for processing message with ChatGPT.
     *
     * @param message Message to be processed, e.g., transcribed voice command.
     */
    abstract public void processWithGippity(String message);

    // ////////////////////////////////////////////////////////////////////// //

    private final List<Observer> observers = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    public List<Observer> getObservers() {
        return observers;
    }

    public static final String MODEL_RESPONSE_EVENT = "MODEL_RESPONSE_EVENT";

    /**
     * Method for pushing model response event.
     *
     * @param message The response message.
     */
    protected void pushModelResponseEvent(String message) {
        logger.debug("pushModelResponseEvent | Triggered.");
        pushEvent(MODEL_RESPONSE_EVENT, message);
    }
}
