package net.eightlives.periodic.core;

import java.util.HashMap;
import java.util.Map;

import net.eightlives.periodic.core.parcel.Parcel;
import net.eightlives.periodic.core.parcel.ParcelData;
import net.eightlives.periodic.core.parcel.ParcelIntent;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * The core of the Periodic framework. Periodic is centered around a dynamic
 * number of {@link Parcel Parcels} being rendered on a {@link PeriodicView}
 * that displays each Parcel according to its own rules. All Parcels should be
 * treated equally with respect to their {@link ParcelIntent}.
 * 
 * @author Zack Brown
 */
@Component
public class PeriodicCore
{
    private final Map<Long, Parcel> parcels = new HashMap<Long, Parcel>();
    private final Map<Parcel, ParcelOpener> parcelOpeners = new HashMap<Parcel, ParcelOpener>();
    private PeriodicView view;

    @Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC)
    protected void bindView(PeriodicView view)
    {
        this.view = view;
        view.launchViewFramework();
        view.addParcelSelectedListener(parcelId ->
        {
            Parcel parcel = parcels.get(parcelId);
            parcel.showParcelContent(view.getNextParcelContainer());
            parcel.notifyOfSelection();
        });
    }

    protected void unbindView(PeriodicView view)
    {
        // TODO kill ui method?
        this.view = null;
    }

    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
    protected void bindParcel(Parcel parcel)
    {
        ParcelData parcelData = parcel.getParcelData();

        long parcelId = parcelData.getParcelId();
        if (parcels.containsKey(parcelId))
        {
            // TODO view framework-level show error message about parcel already
            // existing
        }
        else
        {
            parcels.put(parcelId, parcel);

            ParcelOpener parcelOpener = view.createParcelOpener(parcelData);
            parcelOpeners.put(parcel, parcelOpener);
            parcel.addParcelVisibilityListener(isVisible -> parcelOpener.setVisible(isVisible));
        }
    }

    protected void unbindParcel(Parcel parcel)
    {
        // TODO add API to view to clear content pane or go to last used parcel
        parcelOpeners.get(parcel).destroy();
        parcelOpeners.remove(parcel);
    }
}
