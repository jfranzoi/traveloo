package my.projects.traveloo.flight.domain;

import my.projects.traveloo.flight.infrastructure.InventoryScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Inventory {

    private static Logger LOGGER = LoggerFactory.getLogger(InventoryScheduler.class);

    public void addItinerariesTo(Trip trip) {
        int size = randomSize();
        LOGGER.info("Adding [{}] itineraries, trip: [{}]", size, trip.getId());
        trip.addItineraries(IntStream.rangeClosed(1, size)
                .mapToObj(x -> toItinerary(randomId(), trip))
                .collect(Collectors.toList()));
    }

    private Itinerary toItinerary(String id, Trip trip) {
        Itinerary itinerary = new Itinerary(id);
        itinerary.setHops(Arrays.asList(
                new Hop(new Position(trip.getFrom(), anyTime()), new Position(trip.getTo(), anyTime()))
        ));
        return itinerary;
    }

    private int randomSize() {
        return ThreadLocalRandom.current().nextInt(1, 10);
    }

    private String randomId() {
        return UUID.randomUUID().toString();
    }

    private Instant anyTime() {
        return between(
                Instant.now().plus(1, ChronoUnit.HOURS),
                Instant.now().plus(10, ChronoUnit.DAYS)
        );
    }

    private Instant between(Instant startInclusive, Instant endExclusive) {
        long startSeconds = startInclusive.getEpochSecond();
        long endSeconds = endExclusive.getEpochSecond();
        return Instant.ofEpochSecond(
                ThreadLocalRandom.current().nextLong(startSeconds, endSeconds)
        );
    }
}
