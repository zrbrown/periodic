package net.eightlives.periodic.core.parcel;

/**
 * Contains information about a {@link Parcel}.
 * 
 * @author Zack Brown
 */
public interface ParcelData
{
    /**
     * Returns the identifier of the referenced {@link Parcel}.
     * 
     * @return the identifier of the referenced Parcel
     */
	public long getParcelId();

    /**
     * Returns the display text of the referenced {@link Parcel}.
     * 
     * @return the display text of the referenced Parcels
     */
	public String getDisplayText();

    /**
     * Returns the {@link ParcelIntent} of the referenced {@link Parcel}.
     * 
     * @return the intent of the referenced Parcels
     */
    public ParcelIntent getParcelIntent();
}
