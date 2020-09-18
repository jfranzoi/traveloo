package my.projects.traveloo.flight.resource;

import my.projects.traveloo.flight.domain.Itinerary;
import my.projects.traveloo.flight.domain.Position;
import my.projects.traveloo.flight.domain.Repository;
import my.projects.traveloo.flight.domain.Trip;
import my.projects.traveloo.flight.representation.ItinerariesRepresentation;
import my.projects.traveloo.flight.representation.ItineraryRepresentation;
import my.projects.traveloo.flight.representation.Link;
import my.projects.traveloo.flight.representation.TripRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TripResource {

    private Repository repository;

    @Autowired
    public TripResource(Repository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/trip")
    public ResponseEntity<?> create(@RequestBody TripRepresentation representation, UriComponentsBuilder uri) {
        Trip trip = repository.save(toDomain(representation));
        return ResponseEntity.created(locationToDetail(uri, trip).toUri())
                .build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/trip/{id}")
    public ResponseEntity<?> detail(@PathVariable("id") String id, UriComponentsBuilder uri) {
        return repository.findTripBy(id)
                .map(x -> ResponseEntity.ok().body(toRepresentation(x, uri)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/trip/{id}/itineraries")
    public ResponseEntity<?> itineraries(@PathVariable("id") String id) {
        return repository.findTripBy(id)
                .flatMap(x -> x.getItineraries())
                .map(x -> ResponseEntity.ok().body(toRepresentation(x)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private UriComponents locationToDetail(UriComponentsBuilder uri, Trip trip) {
        return uri.cloneBuilder().path("/trip/{id}").build().expand(trip.getId());
    }

    private UriComponents locationToItineraries(UriComponentsBuilder uri, Trip trip) {
        return uri.cloneBuilder().path("/trip/{id}/itineraries").build().expand(trip.getId());
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
        representation.setDetails(domain.getHops().stream()
                .map(x -> Arrays.asList(
                        details(x.getDeparture()),
                        details(x.getArrival())
                )).flatMap(Collection::stream)
                .collect(Collectors.joining(", ")));
        return representation;
    }

    private String details(Position position) {
        return new StringBuilder()
                .append(position.getPlace())
                .append(" ")
                .append(position.getDate())
                .toString();
    }

    private TripRepresentation toRepresentation(Trip domain, UriComponentsBuilder uri) {
        TripRepresentation representation = new TripRepresentation();
        representation.setFrom(domain.getFrom());
        representation.setTo(domain.getTo());
        representation.setLinks(Arrays.asList(
                Link.create("self", locationToDetail(uri, domain).toUriString()),
                Link.create("itineraries", locationToItineraries(uri, domain).toUriString())
        ));
        return representation;
    }

    private Trip toDomain(TripRepresentation representation) {
        Trip domain = Trip.empty();
        domain.setFrom(representation.getFrom());
        domain.setTo(representation.getTo());
        return domain;
    }
}
