package com.twu.biblioteca.integration.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.WebApp;
import com.twu.biblioteca.database.UserDatabase;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RestResponse;
import com.twu.biblioteca.model.User;
import com.twu.biblioteca.model.request.UserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= WebApp.class)
@RunWith(SpringRunner.class)
public class LoginControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private JacksonTester<RestResponse> itemJson;
    private JacksonTester<UserRequest> requestJson;

    @Before
    public void setup() {
        // Given
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        Biblioteca.getInstance().initialize();
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void shouldLoginWithCorrectUser() throws Exception  {
        // Given
        // Prepare request
        User user = UserDatabase.getInstance().getUsers().get(0);
        UserRequest userRequest = new UserRequest();
        userRequest.setId(user.getId());
        userRequest.setPassword(user.getPassword());
        // Prepare expected result
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS, Label.LOGIN_SUCCESS.text + user.getName());
        String json = itemJson.write(expectedResult).getJson();
        // When
        this.mockMvc.perform(post("/login")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson.write(userRequest).getJson()))
        // Then
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldNotLoginWithWrongUser() throws Exception  {
        // Given
        // Prepare request
        User user = UserDatabase.getInstance().getUsers().get(0);
        UserRequest userRequest = new UserRequest();
        userRequest.setId(user.getId());
        userRequest.setPassword(user.getPassword()+"xxx");
        // Prepare expected result
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.FAIL, Label.LOGIN_FAIL.text);
        String json = itemJson.write(expectedResult).getJson();
        // When
        this.mockMvc.perform(post("/login")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson.write(userRequest).getJson()))
        // Then
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }
}
