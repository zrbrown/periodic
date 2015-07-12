package net.eightlives.periodic.core.parcel;

import net.eightlives.periodic.core.ParcelOpener;

/**
 * The intent of a {@link Parcel}, which indicates what role a Parcel should
 * play in the application lifecycle.
 * 
 * @author Zack Brown
 */
public enum ParcelIntent
{
    /**
     * A standard Parcel is controlled by a {@link ParcelOpener} that may or may
     * not be accessible during the lifecycle of the application (e.g. it can be
     * scrolled off the screen).
     */
    STANDARD,
    /**
     * An apparent Parcel is controlled by a {@link ParcelOpener} that is always
     * accessible during the lifecycle of the application (e.g. a logout
     * button).
     */
    APPARENT;
}
