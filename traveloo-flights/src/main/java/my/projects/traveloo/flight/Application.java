package my.projects.traveloo.flight;

import my.projects.traveloo.flight.domain.Database;
import my.projects.traveloo.flight.domain.InMemoryDatabase;
import my.projects.traveloo.flight.domain.Inventory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public InMemoryDatabase database() {
        return new InMemoryDatabase();
    }

    @Bean
    public Inventory inventory(Database database) {
        return new Inventory(database);
    }
}
