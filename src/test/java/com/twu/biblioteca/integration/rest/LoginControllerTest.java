package com.twu.biblioteca.integration.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twu.biblioteca.App;
import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RestResponse;
import com.twu.biblioteca.model.User;
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
public class LoginControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private JacksonTester<RestResponse> itemJson;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        App.biblioteca = new Biblioteca();
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void shouldLoginWithCorrectUser() throws Exception  {
        User user = App.biblioteca.user().getUsers().get(0);
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS, Label.LOGIN_SUCCESS.text + user.getName());
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/login?id=" + user.getId() + "&password=" + user.getPassword()))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldNotLoginWithWrongUser() throws Exception  {
        User user = App.biblioteca.user().getUsers().get(0);
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.FAIL, Label.LOGIN_FAIL.text);
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/login?id=" + user.getId() + "&password=" + user.getPassword() + "xxx"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }
}
