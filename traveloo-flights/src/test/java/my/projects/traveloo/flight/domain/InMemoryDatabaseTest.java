package my.projects.traveloo.flight.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class InMemoryDatabaseTest {

    private InMemoryDatabase database;

    @Before
    public void setUp() throws Exception {
        database = new InMemoryDatabase();
    }

    @Test
    public void createSequentially() {
        assertThat(database.createTrip(Trip.empty()), hasProperty("id", is("1")));
        assertThat(database.createTrip(Trip.empty()), hasProperty("id", is("2")));
        assertThat(database.createTrip(Trip.empty()), hasProperty("id", is("3")));
    }

    @Test
    public void notFoundById() {
        assertThat(database.findTripBy("any"), is(Optional.empty()));
    }

    @Test
    public void foundById() {
        Trip trip = database.createTrip(Trip.empty());
        assertThat(database.findTripBy("1"), is(Optional.of(trip)));
    }
}