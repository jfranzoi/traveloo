package my.projects.traveloo.flight.resource;

import my.projects.traveloo.flight.representation.StatusRepresentation;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/info/status")
public class StatusResource {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.noCache())
                .body(representationAs("ok"));
    }

    private StatusRepresentation representationAs(String value) {
        StatusRepresentation representation = new StatusRepresentation();
        representation.setValue(value);
        return representation;
    }

}
