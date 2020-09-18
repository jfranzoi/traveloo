package my.projects.traveloo.flight.domain;

import java.util.List;

public class Itinerary {

    private String id;
    private List<Hop> hops;

    public Itinerary(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setHops(List<Hop> hops) {
        this.hops = hops;
    }

    public List<Hop> getHops() {
        return hops;
    }
}
