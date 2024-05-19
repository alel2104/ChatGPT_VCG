package mvc;

import connectcopy.SocketServer;
import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Observer;

/**
 * Class serving as the common superclass for any controller.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
abstract public class Controller implements Observer {
    private static final Logger logger = LoggerFactory.getLogger("mvc.controller");

    // ////////////////////////////////////////////////////////////////////// //

    protected final Model model;
    protected final View view;
    protected final SocketServer server;

    /**
     * Constructor to initialize and attach the model, view, and server components.
     *
     * @param model The model component.
     * @param view The view component.
     * @param server The server component.
     */
    public Controller(Model model, View view, SocketServer server) {
        logger.debug("Constructor() | Initializing.");

        this.model = model;
        this.view = view;
        this.server = server;

        this.model.attachObserver(this);
        this.view.attachObserver(this);
        this.server.attachObserver(this);

        // Starting the WebSocket server.
        server.start();
    }

    // ////////////////////////////////////////////////////////////////////// //

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleEvent(String eventName, Object... data) {
        switch(eventName) {
            case SocketServer.SERVER_INCOMING_EVENT -> handleServerIncomingEvent((WebSocket) data[0], (String) data[1]);
            case View.VIEW_CHANGE_GIPPITY_VARIANT_EVENT -> handleViewChangeGippityVariantEvent((Model.GippityVariants) data[0]);
            case Model.MODEL_RESPONSE_EVENT -> handleModelResponseEvent((String) data[0]);
            default -> logger.warn("handleEvent() | Unhandled event type: {}", eventName);
        }
    }

    /**
     * Method for handling the server incoming event.
     *
     * @param socket The socket which sent the message.
     * @param message The message of the event.
     */
    abstract public void handleServerIncomingEvent(WebSocket socket, String message);

    /**
     * Method for handling the change ChatGPT variant display event.
     *
     * @param variant The current ChatGPT variant.
     */
    abstract public void handleViewChangeGippityVariantEvent(Model.GippityVariants variant);

    /**
     * Method for handling the model response event.
     *
     * @param message The response message of the event.
     */
    abstract public void handleModelResponseEvent(String message);
}
