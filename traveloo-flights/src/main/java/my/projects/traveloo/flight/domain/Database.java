package my.projects.traveloo.flight.domain;

import java.util.Collection;
import java.util.Optional;

public interface Database {
    Trip createTrip();

    Optional<Trip> findTripBy(String id);

    Collection<Trip> allTrips();
}
