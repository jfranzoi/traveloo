package my.projects.traveloo.flight.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryDatabase implements Database {

    private Map<String, Trip> tripById = new HashMap<>();

    @Override
    public Trip createTrip() {
        String nextId = String.valueOf(tripById.size() + 1);
        Trip trip = new Trip(nextId);

        tripById.put(nextId, trip);
        return trip;
    }

    @Override
    public Optional<Trip> findTripBy(String id) {
        return Optional.ofNullable(tripById.get(id));
    }

    @Override
    public Collection<Trip> allTrips() {
        return tripById.values();
    }

}
