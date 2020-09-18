package my.projects.traveloo.flight.infrastructure;

import my.projects.traveloo.flight.domain.Repository;
import my.projects.traveloo.flight.domain.Inventory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Optional;

public class InventoryScheduler {

    private Inventory inventory;
    private Repository repository;

    public InventoryScheduler(Repository repository) {
        this.repository = repository;
        this.inventory = new Inventory();
    }

    @Scheduled(fixedRateString = "${schedules.inventory.rate}")
    public void prepareItineraries() {
        repository.allTrips().stream()
                .filter(x -> none(x.getItineraries()))
                .forEach(x -> inventory.addItinerariesTo(x));
    }

    private <T> boolean none(Optional<List<T>> items) {
        return !items.isPresent();
    }
}
