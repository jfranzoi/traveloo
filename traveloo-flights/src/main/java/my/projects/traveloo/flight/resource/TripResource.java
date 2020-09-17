package my.projects.traveloo.flight.resource;

import my.projects.traveloo.flight.domain.Database;
import my.projects.traveloo.flight.domain.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class TripResource {

    private Database database;

    @Autowired
    public TripResource(Database database) {
        this.database = database;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/trip")
    public ResponseEntity<?> create(UriComponentsBuilder uri) {
        Trip trip = database.createTrip();
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

    private TripRepresentation toRepresentation(Trip id) {
        TripRepresentation representation = new TripRepresentation();
        representation.setId(id.getId());
        return representation;
    }
}
