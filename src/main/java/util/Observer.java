package util;

/**
 * Interface for use as the "listener" in the observer pattern.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
public interface Observer {

    /**
     * Method, which is triggered by observed objects when an event occurs.
     *
     * @param eventName The name of the event.
     * @param data Data relevant to the event, e.g. transcribed voice commands.
     */
    void handleEvent(String eventName, Object... data);
}
