package my.projects.traveloo.flight.resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/info/status")
public class StatusResource {

    @RequestMapping(method = RequestMethod.GET)
    public StatusRepresentation get() {
        return representationAs("ok");
    }

    private StatusRepresentation representationAs(String value) {
        StatusRepresentation representation = new StatusRepresentation();
        representation.setValue(value);
        return representation;
    }

}
