package my.projects.traveloo.flight.domain;

public class Hop {

    private Position departure;
    private Position arrival;

    public Hop(Position departure, Position arrival) {
        this.departure = departure;
        this.arrival = arrival;
    }

    public Position getDeparture() {
        return departure;
    }

    public Position getArrival() {
        return arrival;
    }
}
