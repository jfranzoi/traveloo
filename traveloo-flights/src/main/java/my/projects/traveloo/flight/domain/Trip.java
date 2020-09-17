package my.projects.traveloo.flight.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Trip {

    public static Trip empty() {
        return new Trip(null);
    }

    private String id;
    private String from;
    private String to;
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

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }
}
