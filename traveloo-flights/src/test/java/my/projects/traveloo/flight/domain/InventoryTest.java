package my.projects.traveloo.flight.domain;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class InventoryTest {

    private Trip LON_PAR;

    @Before
    public void setUp() throws Exception {
        LON_PAR = Trip.empty();
        LON_PAR.setFrom("LON");
        LON_PAR.setTo("PAR");
    }

    @Test
    public void addItineraries() {
        new Inventory().addItinerariesTo(LON_PAR);

        assertThat(LON_PAR.getItineraries().get(), not(empty()));
        assertThat(LON_PAR.getItineraries().get(), everyItem(
                hasProperty("hops", not(empty()))
        ));
    }

    @Test
    public void itineraryOnSameRoute() {
        new Inventory().addItinerariesTo(LON_PAR);

        assertThat(LON_PAR.getItineraries().get(), everyItem(
                hasProperty("hops", everyItem(
                        allOf(
                                hasProperty("departure", positionAt("LON")),
                                hasProperty("arrival", positionAt("PAR"))
                        )
                ))
        ));
    }

    private Matcher<Object> positionAt(String place) {
        return allOf(
                hasProperty("place", is(place)),
                hasProperty("date", greaterThan(Instant.now()))
        );
    }
}