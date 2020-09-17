package my.projects.traveloo.flight.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Inventory {

    private static Logger LOGGER = LoggerFactory.getLogger(Inventory.class);

    private Database database;
    private Random random;

    public Inventory(Database database) {
        this.database = database;
        this.random = new Random();
    }

    @Scheduled(fixedRate = 5000)
    public void prepareItineraries() {
        database.allTrips().stream()
                .filter(x -> none(x.getItineraries()))
                .forEach(x -> addItinerariesTo(x));
    }

    private void addItinerariesTo(Trip trip) {
        int size = randomSize();
        LOGGER.info("Adding [{}] itineraries, trip: [{}]", size, trip.getId());
        trip.addItineraries(IntStream.rangeClosed(1, size)
                .mapToObj(x -> new Itinerary(randomId()))
                .collect(Collectors.toList()));
    }

    private int randomSize() {
        return random.nextInt(10);
    }

    private String randomId() {
        return UUID.randomUUID().toString();
    }

    private <T> boolean none(Optional<List<T>> items) {
        return !items.isPresent();
    }
}
