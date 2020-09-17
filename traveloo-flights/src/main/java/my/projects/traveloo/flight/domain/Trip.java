package my.projects.traveloo.flight.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Trip {

    private String id;
    private List<Itinerary> itineraries;

    public Trip(String id) {
        this.id = id;
        this.itineraries = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void addItineraries(List<Itinerary> itineraries) {
        this.itineraries.addAll(itineraries);
    }

    public Optional<List<Itinerary>> getItineraries() {
        return itineraries.isEmpty() ? Optional.empty() : Optional.of(itineraries);
    }
}
