package my.projects.traveloo.flight.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Arrays;
import java.util.UUID;

public class Inventory {

    private static Logger LOGGER = LoggerFactory.getLogger(Inventory.class);

    private Database database;

    public Inventory(Database database) {
        this.database = database;
    }

    @Scheduled(fixedRate = 5000)
    public void prepareItineraries() {
        database.allTrips().stream()
                .filter(x -> !x.getItineraries().isPresent())
                .forEach(x -> addItinerariesTo(x));
    }

    private void addItinerariesTo(Trip trip) {
        LOGGER.info("Adding itineraries, trip: [{}]", trip.getId());
        trip.addItineraries(Arrays.asList(new Itinerary(randomId())));
    }

    private String randomId() {
        return UUID.randomUUID().toString();
    }
}
