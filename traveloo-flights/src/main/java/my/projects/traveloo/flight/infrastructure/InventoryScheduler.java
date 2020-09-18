package my.projects.traveloo.flight.infrastructure;

import my.projects.traveloo.flight.domain.Database;
import my.projects.traveloo.flight.domain.Inventory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Optional;

public class InventoryScheduler {

    private Inventory inventory;
    private Database database;

    public InventoryScheduler(Database database) {
        this.database = database;
        this.inventory = new Inventory();
    }

    @Scheduled(fixedRateString = "${schedules.inventory.rate}")
    public void prepareItineraries() {
        database.allTrips().stream()
                .filter(x -> none(x.getItineraries()))
                .forEach(x -> inventory.addItinerariesTo(x));
    }

    private <T> boolean none(Optional<List<T>> items) {
        return !items.isPresent();
    }
}
