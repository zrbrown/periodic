package net.eightlives.periodic.core;

import net.eightlives.periodic.core.parcel.Parcel;

/**
 * A navigation element that opens its intended {@link Parcel} upon selection.
 * 
 * @author Zack Brown
 */
public interface ParcelOpener
{
    /**
     * Sets whether or not this {@link ParcelOpener} should be visible.
     * 
     * @param isVisible
     *            {@code true} if this Parcel opener should be visible;
     *            {@code false} otherwise
     */
    void setVisible(boolean isVisible);

    /**
     * Destroys this {@link ParcelOpener}. It is no longer visible and should no
     * longer be taken into account in any view layout. After this method is
     * called, it should be as if this Parcel opener never existed.
     */
    void destroy();
}
