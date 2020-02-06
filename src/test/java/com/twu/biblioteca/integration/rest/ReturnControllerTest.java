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
public class ReturnControllerTest {

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
        // Checkout some item
        Rental item = RentalDatabase.getInstance().getItems(RentalDatabase.Filter.AVAILABLE).get(0);
        itemRequest = new ItemRequest();
        itemRequest.setName(item.getTitle());
        Biblioteca.getInstance().doCheckOut(item.getTitle());
    }

    @Test
    public void shouldReturnCorrectItem() throws Exception  {
        // Given
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS, Label.RETURN_SUCCESS.text);
        String json = itemJson.write(expectedResult).getJson();
        // When
        this.mockMvc.perform(post("/return/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson.write(itemRequest).getJson()))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldNotReturnAvailableItem() throws Exception  {
        // Given
        Biblioteca.getInstance().doReturn(itemRequest.getName());
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.FAIL, Label.RETURN_FAIL.text);
        String json = itemJson.write(expectedResult).getJson();
        // When
        this.mockMvc.perform(post("/return/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson.write(itemRequest).getJson()))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldNotReturnWrongItem() throws Exception  {
        // Given
        itemRequest.setName("thereisnoitemwiththisname");
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.FAIL, Label.RETURN_FAIL.text);
        String json = itemJson.write(expectedResult).getJson();
        // When
        this.mockMvc.perform(post("/return/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson.write(itemRequest).getJson()))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldNotReturnWhenNotLogin() throws Exception  {
        // Given
        Biblioteca.getInstance().user().logout();
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.FAIL, Label.AUTHORIZATION_ERROR.text);
        String json = itemJson.write(expectedResult).getJson();
        // When
        this.mockMvc.perform(post("/return/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson.write(itemRequest).getJson()))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldNotReturnOthersItem() throws Exception  {
        // Given
        User user = UserDatabase.getInstance().getUsers().get(1);
        Biblioteca.getInstance().user().login(user.getId(),user.getPassword());
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.FAIL, Label.AUTHORIZATION_ERROR.text);
        String json = itemJson.write(expectedResult).getJson();
        // When
        this.mockMvc.perform(post("/return/")
                .contentType(APPLICATION_JSON_UTF8)
                .content(requestJson.write(itemRequest).getJson()))
                // Then
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }
}
