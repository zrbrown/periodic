package net.eightlives.periodic.core.parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Facilitates communication among {@link Parcel Parcels}.
 * 
 * @author Zack Brown
 */
public class ParcelCommunicator
{
    private static final Map<String, List<Consumer<Object>>> EVENTS_TO_LISTENERS = new HashMap<String, List<Consumer<Object>>>();

    /**
     * Registers a listener to be notified of the specified event.
     * 
     * @param event
     *            the event for which to listen
     * @param listener
     *            the listener to notify when the event occurs
     */
    public static void registerEventListener(String event, Consumer<Object> listener)
    {
        List<Consumer<Object>> listeners = EVENTS_TO_LISTENERS.get(event);

        if (listeners == null)
        {
            listeners = new ArrayList<Consumer<Object>>();
            EVENTS_TO_LISTENERS.put(event, listeners);
        }

        listeners.add(listener);
    }

    /**
     * Stops a listener from being notified of the specified event.
     * 
     * @param event
     *            the event for which to stop listening
     * @param listener
     *            the listener to stop from being notified when the event occurs
     */
    public static void deregisterEventListener(String event, Consumer<Object> listener)
    {
        List<Consumer<Object>> listeners = EVENTS_TO_LISTENERS.get(event);

        if (listeners != null)
        {
            listeners.remove(listener);
        }
    }

    /**
     * Notifies all registered listeners of the specified event.
     * 
     * @param event
     *            the event of which to notify registered listeners
     * @param communicationData
     *            data that pertains to the event. Consumers should be careful
     *            to cast this correctly.
     */
    public static void notifyListenersOfEvent(String event, Object... communicationData)
    {
        List<Consumer<Object>> eventListeners = EVENTS_TO_LISTENERS.get(event);

        if (eventListeners != null)
        {
            eventListeners.forEach(listener -> listener.accept(communicationData));
        }
    }
}
