package my.projects.traveloo.flight.domain;

import java.util.Collection;
import java.util.Optional;

public interface Repository {
    Trip save(Trip trip);

    Optional<Trip> findTripBy(String id);

    Collection<Trip> allTrips();
}
