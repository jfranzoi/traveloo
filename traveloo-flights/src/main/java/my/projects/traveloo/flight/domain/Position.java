package my.projects.traveloo.flight.domain;

import java.time.Instant;

public class Position {

    private String place;
    private Instant date;

    public Position(String place, Instant date) {
        this.place = place;
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public Instant getDate() {
        return date;
    }
}
