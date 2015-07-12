package net.eightlives.periodic.core;

import java.util.function.Consumer;

import net.eightlives.periodic.core.parcel.Parcel;
import net.eightlives.periodic.core.parcel.ParcelData;

/**
 * A view for the periodic framework. Implementations of this define what UI
 * framework should be used by contributing {@link Parcel Parcels}.<br>
 * <br>
 * Implementations of this must register as an OSGi component.<br>
 * <br>
 * Example:
 * 
 * <pre>
 * <code>    {@literal @}Component(service = PeriodicView.class)</code>
 * </pre>
 * 
 * @author Zack Brown
 */
public interface PeriodicView
{
    /**
     * Launches and initializes the view framework.
     */
    public void launchViewFramework();

    /**
     * Creates a Parcel opener, a navigation element that opens its intended
     * {@link Parcel} upon selection, in this view using the specified
     * {@link ParcelData}.
     * 
     * @param parcelData
     *            the data with which to populate the new Parcel opener
     * @return the new {@link ParcelOpener}
     */
    public ParcelOpener createParcelOpener(ParcelData parcelData);

    /**
     * Returns the {@link ParcelContainer} in which to render the next
     * {@link Parcel} requested to be opened.
     * 
     * @return the parcel container in which to render the next parcel requested
     *         to be opened
     */
    public ParcelContainer getNextParcelContainer();

    /**
     * Adds a listener to be notified of parcel selected events.
     * 
     * @param parcelSelectedListener
     *            the listener to be notified
     */
    public void addParcelSelectedListener(Consumer<Long> parcelSelectedListener);

    /**
     * Stops a listener from being notified of parcel selected events.
     * 
     * @param parcelSelectedListener
     *            listener to stop from being notified
     */
    public void removeParcelSelectedListener(Consumer<Long> parcelSelectedListener);
}
