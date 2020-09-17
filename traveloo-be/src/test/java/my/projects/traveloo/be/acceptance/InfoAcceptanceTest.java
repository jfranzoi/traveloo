package my.projects.traveloo.be.acceptance;

import my.projects.traveloo.be.controller.StatusResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class InfoAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void anySiteCORS() throws Exception {
        // see: https://stackoverflow.com/a/41841662
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ORIGIN, "http://any-site");

        ResponseEntity<StatusResponse> entity = template.exchange(
                location("/info/status"), HttpMethod.GET, new HttpEntity<>(headers), StatusResponse.class
        );

        assertThat(entity.getHeaders().get(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN), contains("*"));
    }

    @Test
    public void statusOK() throws Exception {
        ResponseEntity<StatusResponse> entity = template.getForEntity(
                location("/info/status"), StatusResponse.class
        );

        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(entity.getHeaders().get(HttpHeaders.CONTENT_TYPE), contains("application/json"));
        assertThat(entity.getBody(), hasProperty("be", is("ok")));
    }

    private String location(String paths) throws Exception {
        return UriComponentsBuilder.newInstance()
                .scheme("http").host("localhost").port(port)
                .path(paths)
                .toUriString();
    }
}