# Periodic
Periodic is a modular, UI-agnostic framework that runs in an OSGi container.

## View Implementation
Consumers are free to create their own framework view implementations, although [Periodic FX][Periodic FX repo], the reference implementation, is readily available. Periodic must have a view implementation to run, as [PeriodicView][PeriodicView source] is a required static bundle.

## Parcels
A [Parcel][Parcel source] is the primary modular unit. Zero to many can be contributed to a Periodic instance at runtime.

## Project structure
While it would be technically feasible to contribute everything with a single bundle, it is not recommended. Periodic is structured so that the view and controller logic can be easily replaced individually. The documentation enforces this by defining how a [Parcel][Parcel source] and [ParcelViewProvider][ParcelViewProvider source] should be bound together. Further, the actual controller and view implementations within the Parcel are recommended to be contributed from their own bundles, allowing them to be used in another framework with little to no refactoring.

An ideal project structure (using the Periodic FX implementation) will therefore contain 4 bundles:
* com.example.phoneorder.parcel (containing Parcel implementation)
* com.example.phoneorder.parcel.fx (containing ParcelViewProvider implementation)
* com.example.phoneorder.ui (controller logic; should be consumed by com.example.phoneorder.parcel)
* com.example.phoneorder.ui.fx (view logic; should be consumed by com.example.phoneorder.parcel.fx)

## Requirements
* Java 8 or greater
* OSGi R4 or greater

[Periodic FX repo]: https://github.com/zrbrown/periodic-fx
[PeriodicView source]: ./src/main/java/net/eightlives/periodic/core/PeriodicView.java
[Parcel source]: ./src/main/java/net/eightlives/periodic/core/parcel/Parcel.java
[ParcelViewProvider source]: ./src/main/java/net/eightlives/periodic/core/parcel/ParcelViewProvider.java
