package my.projects.traveloo.flight.resource;

import my.projects.traveloo.flight.domain.InMemoryDatabase;
import my.projects.traveloo.flight.domain.Itinerary;
import my.projects.traveloo.flight.domain.Trip;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TripResourceTest {

    private MockMvc mvc;
    private InMemoryDatabase database;

    @Before
    public void setUp() throws Exception {
        database = new InMemoryDatabase();
        mvc = MockMvcBuilders.standaloneSetup(new TripResource(database))
                .build();
    }

    @Test
    public void requestNewTrip() throws Exception {
        mvc.perform(post("/trip")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"from\":\"LON\",\"to\":\"PAR\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/trip/1"));
    }

    @Test
    public void tripDetailsFound() throws Exception {
        database.createTrip();
        mvc.perform(get("/trip/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void tripDetailsNotFound() throws Exception {
        mvc.perform(get("/trip/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void tripItinerariesNotReady() throws Exception {
        database.createTrip();
        mvc.perform(get("/trip/1/itineraries"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void tripItinerariesReady() throws Exception {
        Trip trip = database.createTrip();
        trip.addItineraries(Arrays.asList(new Itinerary("123")));

        mvc.perform(get("/trip/1/itineraries"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}