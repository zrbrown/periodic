package net.eightlives.periodic.core.parcel;

import net.eightlives.periodic.core.ParcelContainer;

/**
 * Provides the view for a {@link Parcel} to which it is bound. <br>
 * <br>
 * Implementations of this must register as an OSGi component with the
 * PARCEL_VIEW_ID property set. <br>
 * <br>
 * Example:
 * 
 * <pre>
 * <code>    {@literal @}Component(service = ParcelViewProvider.class, property = "PARCEL_VIEW_ID=id")</code>
 * </pre>
 * 
 * It is recommended that the value for PARCEL_VIEW_ID be set to the fully
 * qualified class name of T (e.g. com.example.ExampleView).
 * 
 * @author Zack Brown
 *
 * @param <T>
 *            The view type. The {@link Parcel} this provider is bound to should
 *            know this type.
 */
public interface ParcelViewProvider<T>
{
    /**
     * Displays the bound Parcel's view in the specified {@link ParcelContainer}
     * .
     * 
     * @param container
     *            the {@link ParcelContainer} in which to show the view
     * @return the Parcel's view
     */
    T showView(ParcelContainer container);
}
