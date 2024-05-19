package util;

import java.util.List;

/**
 * Interface used as the "subject" in the observer pattern.
 *
 * @author Martin K. Herkules (makr1906) & Albin Eliasson (alel2104)
 */
public interface Observable {

    /**
     * Method for getting a list of the current observers.
     *
     * @return List of current observers.
     */
    List<Observer> getObservers();

    /**
     * Method for attaching an observer object to listen of object events.
     *
     * @param observer The observer to attach.
     */
    default void attachObserver(Observer observer) {
        if(!getObservers().contains(observer))
            getObservers().add(observer);
    }

    /**
     * Method for detaching an observer.
     *
     * @param observer The observer to detach.
     */
    default void detachObserver(Observer observer) {
        getObservers().remove(observer);
    }

    /**
     * Method for pushing an event to the observers.
     *
     * @param eventName The name of the event.
     * @param data The data of the event.
     */
    default void pushEvent(String eventName, Object... data) {
        getObservers().forEach(o -> o.handleEvent(eventName, data));
    }
}
