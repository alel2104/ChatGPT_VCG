package mvc;

import com.cjcrafter.openai.chat.ChatMessage;
import gippity.GippityFourDirect;
import gippity.GippityFourKeyword;
import gippity.GippityThreeFiveDirect;
import gippity.GippityThreeFiveKeyword;
import gippity.Gippity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Observer;

/**
 * The app model component containing a concrete implementation of a model.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
public class AppModel extends Model implements Observer {
    private static final Logger logger = LoggerFactory.getLogger("mvc.model");

    // ////////////////////////////////////////////////////////////////////// //

    private GippityVariants gippityVariant;
    private Gippity gippity;

    /**
     * Constructor to set the ChatGPT version 4.0 with code keyword
     * generation as starting option.
     */
    public AppModel() {
        logger.debug("Constructor() | Initializing.");

        this.setGippityVariant(GippityVariants.FourKeyword);
    }

    // ////////////////////////////////////////////////////////////////////// //

    /**
     * {@inheritDoc}
     */
    @Override
    public GippityVariants getGippityVariant() {
        logger.debug("getGippityVariant() | Variant: {}", this.gippityVariant);

        return this.gippityVariant;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGippityVariant(GippityVariants variant) {
        logger.debug("setGippityVariant() | Variant: {}", variant.toString());

        if(this.gippity != null)
            this.gippity.detachObserver(this);

        this.gippity = switch(variant) {
            case ThreeFiveKeyword -> new GippityThreeFiveKeyword();
            case ThreeFiveDirect -> new GippityThreeFiveDirect();
            case FourKeyword -> new GippityFourKeyword();
            case FourDirect -> new GippityFourDirect();
        };

        this.gippity.attachObserver(this);

        this.gippityVariant = variant;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processWithGippity(String message) {
        logger.debug("processWithGippity() | Message: {}", message);

        this.gippity.sendMessage(message);
    }

    // ////////////////////////////////////////////////////////////////////// //

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleEvent(String eventName, Object... data) {
        switch(eventName) {
            case Gippity.GIPPITY_OUTGOING_EVENT -> handleGippityOutgoingEvent((ChatMessage) data[0]);
            default -> logger.warn("handleEvent() | Unhandled event type: {}", eventName);
        }
    }

    /**
     * Method for handling the ChatGPT response event.
     *
     * @param message The response message of the event.
     */
    public void handleGippityOutgoingEvent(ChatMessage message) {
        logger.debug("handleGippityOutgoingEvent() | Message: {}", message);
        pushModelResponseEvent((this.gippity.getClass().getSimpleName().contains("Direct") ? "direct " : "keyword ") + message.getContent());
    }
}
