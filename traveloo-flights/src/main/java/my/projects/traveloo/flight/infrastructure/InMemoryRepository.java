package my.projects.traveloo.flight.infrastructure;

import my.projects.traveloo.flight.domain.Repository;
import my.projects.traveloo.flight.domain.Trip;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository implements Repository {

    private Map<String, Trip> tripById = new HashMap<>();

    @Override
    public Trip save(Trip trip) {
        trip.setId(nextId());
        tripById.put(trip.getId(), trip);
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

    private String nextId() {
        return String.valueOf(tripById.size() + 1);
    }

}
