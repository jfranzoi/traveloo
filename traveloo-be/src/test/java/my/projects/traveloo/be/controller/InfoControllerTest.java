package my.projects.traveloo.be.controller;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.MockServerRule;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockserver.model.HttpRequest.request;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class InfoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Rule
    public MockServerRule mockServerRule = new MockServerRule(this, 9090);
    private MockServerClient dependencies;

    @Test
    public void statusFromDependencies() throws Exception {
        dependencies.when(request("/traveloo-flights/info/status").withMethod("GET"))
                .respond(HttpResponse.response()
                        .withStatusCode(200).withHeader("Content-Type", "application/json")
                        .withBody("{ \"value\": \"failure\" }"));

        mvc.perform(get("/info/status").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{ 'global': 'failure', 'be': 'ok', 'flights': 'failure' }"));
    }
}