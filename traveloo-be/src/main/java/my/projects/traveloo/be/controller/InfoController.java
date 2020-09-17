package my.projects.traveloo.be.controller;

import my.projects.traveloo.be.domain.FlightClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/info")
public class InfoController {

    private static final String STATUS_OK = "ok";

    @Autowired
    private FlightClient flightClient;

    @RequestMapping(method = RequestMethod.GET, path = "/status")
    public StatusResponse status() {
        return statusAt(flightClient.status().getValue(), STATUS_OK);
    }

    private StatusResponse statusAt(String flights, String be) {
        StatusResponse response = new StatusResponse();
        response.setFlights(flights);
        response.setBe(be);
        response.setGlobal(Stream.of(flights, be)
                .filter(x -> !x.equals(STATUS_OK))
                .findFirst().orElse(STATUS_OK)
        );
        return response;
    }
}
