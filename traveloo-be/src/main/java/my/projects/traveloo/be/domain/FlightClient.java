package my.projects.traveloo.be.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class FlightClient {

    private static Logger LOGGER = LoggerFactory.getLogger(FlightClient.class);

    private RestTemplate template;
    private String location;

    public FlightClient(String location) {
        this(location, new RestTemplate());
    }

    public FlightClient(String location, RestTemplate restTemplate) {
        LOGGER.info("Configured, location: [{}]", location);
        this.location = location;
        this.template = restTemplate;
    }

    public StatusRepresentation status() {
        try {
            ResponseEntity<StatusRepresentation> entity = template.getForEntity(
                    at("/info/status"), StatusRepresentation.class
            );

            return entity.getBody();
        } catch (Exception e) {
            LOGGER.error("Could not get status", e);
            return representationAt("failure");
        }
    }

    private StatusRepresentation representationAt(String value) {
        StatusRepresentation representation = new StatusRepresentation();
        representation.setValue(value);
        return representation;
    }

    private String at(String path) {
        return UriComponentsBuilder.fromUriString(location)
                .path(path)
                .toUriString();
    }
}
