package mvc;

import connectcopy.SocketServer;
import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The app controller component containing a concrete implementation of a controller.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
public class AppController extends Controller {
    private static final Logger logger = LoggerFactory.getLogger("mvc.controller");

    // ////////////////////////////////////////////////////////////////////// //

    /**
     * Constructor to initialize the model, view, and server components in the
     * superclass constructor.
     *
     * @param model The model component.
     * @param view The view component.
     * @param server The server component.
     */
    public AppController(Model model, View view, SocketServer server) {
        super(model, view, server);

        logger.debug("Constructor() | Initializing.");
    }

    // ////////////////////////////////////////////////////////////////////// //

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleServerIncomingEvent(WebSocket socket, String message) {
        logger.debug("handleServerIncomingEvent() | Socket: {} | Message: {}", socket, message);
        model.processWithGippity(message);
        view.updateCurrentStatusMessage("Command received!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleViewChangeGippityVariantEvent(Model.GippityVariants variant) {
        logger.debug("handleViewChangeGippityVariantEvent | Variant: {}", variant.toString());

        this.model.setGippityVariant(variant);
        this.view.updateGippityVariantDisplay(this.model.getGippityVariant());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleModelResponseEvent(String message) {
        logger.debug("handleModelResponseEvent | Message: {}", message);
        this.server.sendMessage(message);
        view.updateCurrentStatusMessage("Executing command!");
    }
}
