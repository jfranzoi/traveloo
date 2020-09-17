package my.projects.traveloo.be.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/info")
public class InfoController {

    @RequestMapping(method = RequestMethod.GET, path = "/status")
    public StatusResponse status() {
        return statusAt("ok");
    }

    private StatusResponse statusAt(String value) {
        StatusResponse response = new StatusResponse();
        response.setValue(value);
        return response;
    }
}
