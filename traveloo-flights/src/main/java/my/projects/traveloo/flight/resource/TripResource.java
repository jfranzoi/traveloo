package my.projects.traveloo.flight.resource;

import my.projects.traveloo.flight.domain.Database;
import my.projects.traveloo.flight.domain.Itinerary;
import my.projects.traveloo.flight.domain.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TripResource {

    private Database database;

    @Autowired
    public TripResource(Database database) {
        this.database = database;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/trip")
    public ResponseEntity<?> create(@RequestBody TripRepresentation representation, UriComponentsBuilder uri) {
        Trip trip = database.createTrip(toDomain(representation));
        return ResponseEntity.created(uri.path("/trip/{id}")
                .buildAndExpand(trip.getId()).toUri())
                .build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/trip/{id}")
    public ResponseEntity<?> detail(@PathVariable("id") String id) {
        return database.findTripBy(id)
                .map(x -> ResponseEntity.ok().body(toRepresentation(x)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/trip/{id}/itineraries")
    public ResponseEntity<?> itineraries(@PathVariable("id") String id) {
        return database.findTripBy(id)
                .flatMap(x -> x.getItineraries())
                .map(x -> ResponseEntity.ok().body(toRepresentation(x)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ItinerariesRepresentation toRepresentation(List<Itinerary> domains) {
        ItinerariesRepresentation representation = new ItinerariesRepresentation();
        representation.setItineraries(domains.stream()
                .map(x -> toRepresentation(x))
                .collect(Collectors.toList()));
        return representation;
    }

    private ItineraryRepresentation toRepresentation(Itinerary domain) {
        ItineraryRepresentation representation = new ItineraryRepresentation();
        representation.setId(domain.getId());
        return representation;
    }

    private TripRepresentation toRepresentation(Trip domain) {
        TripRepresentation representation = new TripRepresentation();
        representation.setFrom(domain.getFrom());
        representation.setTo(domain.getTo());
        return representation;
    }

    private Trip toDomain(TripRepresentation representation) {
        Trip domain = Trip.empty();
        domain.setFrom(representation.getFrom());
        domain.setTo(representation.getTo());
        return domain;
    }
}
