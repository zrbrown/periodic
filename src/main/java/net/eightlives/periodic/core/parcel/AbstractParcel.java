package net.eightlives.periodic.core.parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import net.eightlives.periodic.core.ParcelContainer;

/**
 * An abstract Parcel that consumers are recommended to extend for convenience
 * and conformity. Implementing classes must still register as and bind to
 * proper OSGi Components (see {@link Parcel} documentation).
 * 
 * @author Zack Brown
 *
 * @param <T>
 *            The view type. This needs to match with the generic type T in the
 *            {@link ParcelViewProvider} that is bound to this Parcel.
 */
public abstract class AbstractParcel<T> implements Parcel
{
    private final List<Consumer<Boolean>> visibilityListeners = new ArrayList<Consumer<Boolean>>();
    private boolean contentCreated;

    @Override
    public ParcelData getParcelData()
    {
        return new ParcelData()
        {
            @Override
            public long getParcelId()
            {
                return getId();
            }

            @Override
            public String getDisplayText()
            {
                return createDisplayText();
            }

            @Override
            public ParcelIntent getParcelIntent()
            {
                return getIntent();
            }
        };
    }

    @Override
    public void showParcelContent(ParcelContainer parcelContainer)
    {
        T view = getParcelViewProvider().showView(parcelContainer);

        if (!contentCreated)
        {
            createParcelController(view);
            contentCreated = true;
        }
    }

    @Override
    public void addParcelVisibilityListener(Consumer<Boolean> visibilityListener)
    {
        visibilityListeners.add(visibilityListener);
    }

    @Override
    public void removeParcelVisibilityListener(Consumer<Boolean> visibilityListener)
    {
        visibilityListeners.remove(visibilityListener);
    }

    @Override
    public void notifyOfSelection()
    {
    }

    /**
     * Notifies visibility listeners added from
     * {@link #addParcelVisibilityListener(Consumer)} of a visibility change
     * event.
     * 
     * @param isVisible
     *            {@code true} to indicate visible; {@code false} otherwise
     */
    protected void notifyVisibilityListeners(boolean isVisible)
    {
        visibilityListeners.forEach(listener -> listener.accept(isVisible));
    }

    /**
     * Creates the controller for the content contained in this {@link Parcel}.
     * 
     * @param view
     *            the view on which to render the controller's content
     */
    protected abstract void createParcelController(T view);

    /**
     * Returns the {@link ParcelViewProvider} bound to this {@link Parcel}.
     * 
     * @return the Parcel view provider bound to this Parcel
     */
    protected abstract ParcelViewProvider<T> getParcelViewProvider();

    /**
     * Returns this {@link Parcel Parcel's} identifier.
     * 
     * @return this Parcel's identifier
     */
    protected abstract long getId();

    /**
     * Returns the display text of this {@link Parcel}.
     * 
     * @return the display text of this Parcel
     */
    protected abstract String createDisplayText();

    /**
     * Returns this {@link Parcel Parcel's} {@link ParcelIntent}.
     * 
     * @return this Parcel's intent
     */
    protected abstract ParcelIntent getIntent();
}
