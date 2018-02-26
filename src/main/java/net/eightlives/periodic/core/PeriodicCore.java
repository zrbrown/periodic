package net.eightlives.periodic.core;

import net.eightlives.periodic.core.parcel.Parcel;
import net.eightlives.periodic.core.parcel.ParcelData;
import net.eightlives.periodic.core.parcel.ParcelIntent;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ResolvedModule;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The core of the Periodic framework. Periodic is centered around a dynamic
 * number of {@link Parcel Parcels} being rendered on a {@link PeriodicView}
 * that displays each Parcel according to its own rules. All Parcels should be
 * treated equally with respect to their {@link ParcelIntent}.
 *
 * @author Zack Brown
 */
public class PeriodicCore {

    private final Map<Long, Parcel> parcels = new HashMap<>();
    private final Map<Parcel, ParcelOpener> parcelOpeners = new HashMap<>();
    private PeriodicView view;

    public static void main(String[] args) {
        PeriodicCore core = new PeriodicCore();
        core.bindView();
    }

    protected void bindView() {
        Optional<ServiceLoader.Provider<PeriodicView>> providedView = ServiceLoader.load(PeriodicView.class).stream().findFirst();
        this.view = providedView.orElseThrow(() -> new RuntimeException("Implementation of PeriodicView must be provided via JPMS")).get();

        view.launchViewFramework();
        view.addParcelSelectedListener(parcelId -> {
            Parcel parcel = parcels.get(parcelId);
            parcel.showParcelContent(view.getNextParcelContainer());
            parcel.notifyOfSelection();
        });
    }

    //TODO put this is a daemon that watches a directory
    protected void loadModule() {
        ModuleFinder finder = ModuleFinder.of(/*dir1, dir2, dir3*/);
        Configuration configuration = ModuleLayer.boot().configuration().resolveAndBind(finder, ModuleFinder.of(), Set.of("myapp"));
        configuration.modules().forEach(module -> System.out.format("%s -> %s%n",
                module.name(),
                module.reads().stream()
                        .map(ResolvedModule::name)
                        .collect(Collectors.joining(", "))));
        ClassLoader classLoader;//TODO new CustomClassLoader

        ModuleLayer parcelModuleLayer = ModuleLayer.defineModulesWithManyLoaders(
                configuration,
                Collections.singletonList(ModuleLayer.boot()),
                PeriodicCore.class.getClassLoader())
                .layer();

        Optional<ServiceLoader.Provider<Parcel>> parcel = ServiceLoader.load(parcelModuleLayer, Parcel.class).stream().findFirst();
        if (parcel.isPresent()) {
            bindParcel(parcel.get().get());
        } else {
            //TODO log error
        }
    }

    protected void bindParcel(Parcel parcel) {
        ParcelData parcelData = parcel.getParcelData();

        long parcelId = parcelData.getParcelId();
        if (parcels.containsKey(parcelId)) {
            // TODO view framework-level show error message about parcel already
            // existing
        } else {
            parcels.put(parcelId, parcel);

            ParcelOpener parcelOpener = view.createParcelOpener(parcelData);
            parcelOpeners.put(parcel, parcelOpener);
            parcel.addParcelVisibilityListener(parcelOpener::setVisible);
        }
    }

    protected void unbindParcel(Parcel parcel) {
        // TODO add API to view to clear content pane or go to last used parcel
        parcelOpeners.get(parcel).destroy();
        parcelOpeners.remove(parcel);
    }
}
