package my.projects.traveloo.flight.domain;

import java.util.Optional;

public interface Database {
    Trip createTrip();

    Optional<Trip> findTripBy(String id);
}
