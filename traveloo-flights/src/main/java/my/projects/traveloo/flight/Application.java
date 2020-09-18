package my.projects.traveloo.flight;

import my.projects.traveloo.flight.domain.Database;
import my.projects.traveloo.flight.infrastructure.InMemoryDatabase;
import my.projects.traveloo.flight.infrastructure.InventoryScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

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
    public InventoryScheduler inventory(Database database) {
        return new InventoryScheduler(database);
    }

    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.OAS_30).select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }
}
