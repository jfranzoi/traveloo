package my.projects.traveloo.be;

import my.projects.traveloo.be.controller.StatusResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InfoAcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void statusOK() throws Exception {
        ResponseEntity<StatusResponse> entity = template.getForEntity(
                location("/info/status"), StatusResponse.class
        );

        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(entity.getHeaders().get(HttpHeaders.CONTENT_TYPE), contains("application/json"));

        assertThat(entity.getBody(), hasProperty("value", is("ok")));
    }

    private String location(String paths) throws Exception {
        return UriComponentsBuilder.newInstance()
                .scheme("http").host("localhost").port(port)
                .path(paths)
                .toUriString();
    }
}