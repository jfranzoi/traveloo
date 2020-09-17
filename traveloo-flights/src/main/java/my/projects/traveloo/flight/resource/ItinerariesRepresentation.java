package my.projects.traveloo.flight.resource;

import java.util.List;

public class ItinerariesRepresentation {
    private List<ItineraryRepresentation> itineraries;

    public void setItineraries(List<ItineraryRepresentation> itineraries) {
        this.itineraries = itineraries;
    }

    public List<ItineraryRepresentation> getItineraries() {
        return itineraries;
    }
}
