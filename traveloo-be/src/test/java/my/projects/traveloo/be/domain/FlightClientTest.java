package my.projects.traveloo.be.domain;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withException;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class FlightClientTest {

    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;

    @Before
    public void setUp() throws Exception {
        restTemplate = new RestTemplate();
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void onSuccess() {
        FlightClient client = new FlightClient("http://flights", restTemplate);

        mockServer.expect(requestTo("http://flights/info/status"))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                        .body("{ \"value\": \"ok\" }"));

        assertThat(client.status(), hasProperty("value", is("ok")));
    }

    @Test
    public void onTimeout() {
        FlightClient client = new FlightClient("http://flights", restTemplate);
        mockServer.expect(requestTo("http://flights/info/status"))
                .andRespond(withException(new SocketTimeoutException()));

        assertThat(client.status(), hasProperty("value", is("failure")));
    }
}