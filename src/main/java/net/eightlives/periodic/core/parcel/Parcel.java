package net.eightlives.periodic.core.parcel;

import java.util.function.Consumer;

import net.eightlives.periodic.core.ParcelContainer;
import net.eightlives.periodic.core.ParcelOpener;

/**
 * A Parcel is the primary modular unit in the Periodic framework.
 * Self-contained and encompassing a specific purpose, a Parcel could be
 * considered a standalone application but for its API to interact with the
 * framework and use of the {@link ParcelCommunicator}. <br>
 * <br>
 * Implementations of this need to register as an OSGi Component.<br>
 * <br>
 * Example:
 * 
 * <pre>
 * <code>    {@literal @}Component(service = Parcel.class)</code>
 * </pre>
 * 
 * Implementations must also bind to a {@link ParcelViewProvider}.<br>
 * <br>
 * Example:
 * 
 * <pre>
 * <code>    {@literal @}Reference(target = "(PARCEL_VIEW_ID=com.example.ExampleView)")
 *     protected void bindParcelViewProvider(ParcelViewProvider{@literal <ExampleView>} parcelViewProvider)
 *     {
 *         ...
 *     }
 * 
 *     protected void unbindParcelViewProvider(ParcelViewProvider{@literal <ExampleView>} parcelViewProvider)
 *     {
 *         ...
 *     }
 * </code>
 * </pre>
 * 
 * @author Zack Brown
 */
public interface Parcel
{
    /**
     * Returns the {@link ParcelData} containing information about this Parcel.
     * 
     * @return data containing information about this Parcel
     */
    public ParcelData getParcelData();

    /**
     * Shows this Parcel in the specified {@link ParcelContainer}.
     * 
     * @param parcelContainer
     *            the container in which to show this Parcel
     */
    public void showParcelContent(ParcelContainer parcelContainer);

    /**
     * Notifies this Parcel that it has been selected. This typically indicates
     * that this Parcel's {@link ParcelOpener} has been selected.
     */
    public void notifyOfSelection();

    /**
     * Adds a listener to be notified when this Parcel becomes visible or
     * invisible.
     * 
     * @param visibilityListener
     *            listener to be notified
     */
    public void addParcelVisibilityListener(Consumer<Boolean> visibilityListener);

    /**
     * Stops a listener from being notified when this Parcel becomes visible or
     * invisible.
     * 
     * @param visibilityListener
     *            listener to stop from being notified
     */
    public void removeParcelVisibilityListener(Consumer<Boolean> visibilityListener);
}
