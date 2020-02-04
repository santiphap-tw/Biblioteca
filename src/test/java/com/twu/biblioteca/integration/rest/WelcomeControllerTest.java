package com.twu.biblioteca.integration.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twu.biblioteca.App;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RestResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= App.class)
@RunWith(SpringRunner.class)
public class WelcomeControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private JacksonTester<RestResponse> itemJson;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void shouldShowWelcomeMessage() throws Exception {
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS, Label.WELCOME.text);
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }
}