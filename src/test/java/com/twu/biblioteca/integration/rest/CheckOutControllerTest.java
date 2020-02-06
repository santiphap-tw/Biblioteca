package com.twu.biblioteca.integration.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.WebApp;
import com.twu.biblioteca.database.RentalDatabase;
import com.twu.biblioteca.database.UserDatabase;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.Rental;
import com.twu.biblioteca.model.RestResponse;
import com.twu.biblioteca.model.User;
import com.twu.biblioteca.model.request.ItemRequest;
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
public class CheckOutControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private JacksonTester<RestResponse> itemJson;
    private JacksonTester<ItemRequest> requestJson;
    private ItemRequest itemRequest;

    @Before
    public void setup() {
        // Given
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        Biblioteca.getInstance().initialize();
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);
        // Login with some user
        User user = UserDatabase.getInstance().getUsers().get(0);
        Biblioteca.getInstance().user().login(user.getId(),user.getPassword());
        // Get some item
        Rental item = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.AVAILABLE).get(0);
        itemRequest = new ItemRequest();
        itemRequest.setName(item.getTitle());
    }

    @Test
    public void shouldCheckOutCorrectItem() throws Exception  {
        // Given
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS, Label.CHECKOUT_SUCCESS.text);
        String json = itemJson.write(expectedResult).getJson();
        // When
        this.mockMvc.perform(post("/checkout")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson.write(itemRequest).getJson()))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldNotCheckOutNAItem() throws Exception  {
        // Given
        Biblioteca.getInstance().doCheckOut(itemRequest.getName());
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.FAIL, Label.CHECKOUT_FAIL.text);
        String json = itemJson.write(expectedResult).getJson();
        // When
        this.mockMvc.perform(post("/checkout")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson.write(itemRequest).getJson()))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldNotCheckOutWrongItem() throws Exception  {
        // Given
        itemRequest.setName("thereisnoitemwiththisname");
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.FAIL, Label.CHECKOUT_FAIL.text);
        String json = itemJson.write(expectedResult).getJson();
        // When
        this.mockMvc.perform(post("/checkout")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson.write(itemRequest).getJson()))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldNotCheckOutWhenNotLogin() throws Exception  {
        // Given
        Biblioteca.getInstance().user().logout();
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.FAIL, Label.AUTHORIZATION_ERROR.text);
        String json = itemJson.write(expectedResult).getJson();
        // When
        this.mockMvc.perform(post("/checkout")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson.write(itemRequest).getJson()))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }
}
