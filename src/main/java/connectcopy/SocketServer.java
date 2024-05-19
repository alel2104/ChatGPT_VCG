package connectcopy;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Observable;
import util.Observer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class serving as the WeSocket server used for message passing between the
 * Serenade script.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
public class SocketServer extends WebSocketServer implements Observable {
    private static final Logger logger = LoggerFactory.getLogger("server");

    private final List<Observer> observers = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    public List<Observer> getObservers() {
        return observers;
    }

    public static final String SERVER_START_EVENT = "SERVER_START_EVENT";
    public static final String SERVER_OPEN_EVENT = "SERVER_OPEN_EVENT";
    public static final String SERVER_CLOSE_EVENT = "SERVER_CLOSE_EVENT";
    public static final String SERVER_INCOMING_EVENT = "SERVER_INCOMING_EVENT";
    public static final String SERVER_OUTGOING_EVENT = "SERVER_OUTGOING_EVENT";
    public static final String SERVER_ERROR_EVENT = "SERVER_ERROR_EVENT";

    /**
     * Method for pushing the start server event.
     */
    private void pushServerStartEvent() {
        logger.debug("pushServerStartEvent | Triggered.");
        pushEvent(SERVER_START_EVENT);
    }

    /**
     * Method for pushing the open server event, when clients connect.
     *
     * @param socket The client WebSocket.
     */
    private void pushServerOpenEvent(WebSocket socket) {
        logger.debug("pushServerOpenEvent | Triggered.");
        pushEvent(SERVER_OPEN_EVENT, socket);
    }

    /**
     * Method for pushing the close server event, when clients disconnect.
     *
     * @param socket The client WebSocket.
     */
    private void pushServerCloseEvent(WebSocket socket) {
        logger.debug("pushServerCloseEvent | Socket: {}", socket);
        pushEvent(SERVER_CLOSE_EVENT, socket);
    }

    /**
     * Method for pushing incoming message event, when clients send a message.
     *
     * @param socket The client WebSocket.
     * @param message The client message.
     */
    private void pushServerIncomingEvent(WebSocket socket, String message) {
        logger.debug("pushServerIncomingEvent | Socket: {} | Message: {}", socket, message);
        pushEvent(SERVER_INCOMING_EVENT, socket, message);
    }

    /**
     * Method for pushing outgoing message event, when the server sends a
     * message to clients.
     *
     * @param message The server message.
     */
    private void pushServerOutgoingEvent(String message) {
        logger.debug("pushServerOutgoingEvent | Message: {}", message);
        pushEvent(SERVER_OUTGOING_EVENT, message);
    }

    /**
     * Method for pushing the server error event, when client error occurs.
     *
     * @param socket The client WebSocket.
     * @param exception The exception.
     */
    private void pushServerErrorEvent(WebSocket socket, Exception exception) {
        logger.debug("pushServerErrorEvent | Socket: {} | Message: {}", socket, exception.getMessage());
        pushEvent(SERVER_ERROR_EVENT, socket, exception);
    }

    // ////////////////////////////////////////////////////////////////////// //

    private static final int PORT = 4444;

    private final Set<WebSocket> sockets;

    /**
     * Constructor to initialize the client WebSocket container and SocketAddress
     * in superclass constructor.
     */
    public SocketServer() {
        super(new InetSocketAddress(PORT));

        logger.info("Constructor() | Initializing.");

        this.sockets = new HashSet<>();
    }

    // ////////////////////////////////////////////////////////////////////// //

    /**
     * Method for adding client WebSocket in container and pushing the open server
     * event when clients connect.
     *
     * @param webSocket The client WebSocket.
     * @param clientHandshake The client TCP handshake.
     */
    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        this.sockets.add(webSocket);

        this.pushServerOpenEvent(webSocket);
    }

    /**
     * Method for removing client WebSocket from container and pushing the close server
     * event when clients disconnect.
     *
     * @param webSocket The client WebSocket.
     * @param code The closing code.
     * @param reason The disconnect reason.
     * @param remote True if disconnect was initialized by remote host.
     */
    @Override
    public void onClose(WebSocket webSocket, int code, String reason, boolean remote) {
        this.sockets.remove(webSocket);

        this.pushServerCloseEvent(webSocket);
    }

    /**
     * Method for pushing the server incoming event from client WebSocket message.
     *
     * @param webSocket The client WebSocket.
     * @param message The client message.
     */
    @Override
    public void onMessage(WebSocket webSocket, String message) {
        this.pushServerIncomingEvent(webSocket, message);
    }

    /**
     * Method removing client WebSocket from container and for pushing the server
     * error event.
     *
     * @param webSocket The client WebSocket.
     * @param exception The exception.
     */
    @Override
    public void onError(WebSocket webSocket, Exception exception) {
        this.sockets.remove(webSocket);

        this.pushServerErrorEvent(webSocket, exception);
    }

    /**
     * Method for pushing the start server event when the server starts.
     */
    @Override
    public void onStart() {
        this.pushServerStartEvent();
    }

    // ////////////////////////////////////////////////////////////////////// //

    /**
     * Method for sending a message to connected client WebSockets and pushing the
     * outgoing message event.
     *
     * @param message The message to be sent.
     */
    public void sendMessage(String message) {
        logger.debug("sendMessage() | Message: {}", message);

        this.pushServerOutgoingEvent(message);

        this.sockets.forEach(socket -> {
            logger.debug("sendMessage() | Socket: {} | Message: {}", socket, message);
            socket.send(message);
        });
    }
}
