package com.twu.biblioteca.integration.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twu.biblioteca.App;
import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.*;
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

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes= App.class)
@RunWith(SpringRunner.class)
public class ShowControllerTest {

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
    public void shouldShowRentalAvailableWhenNoParam() throws Exception {
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS,
                App.biblioteca.getItems(Biblioteca.FILTER.AVAILABLE));
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/show"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldShowRentalAvailableWhenAvailableParam() throws Exception {
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS,
                App.biblioteca.getItems(Biblioteca.FILTER.AVAILABLE));
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/show/available"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldShowRentalNotAvailableWhenNotAvailableParam() throws Exception {
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS,
                App.biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE));
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/show/not_available"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldShowRentalAllWhenAllParam() throws Exception {
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS,
                App.biblioteca.getItems(Biblioteca.FILTER.ALL));
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/show/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldShowBookAvailableWhenBookParam() throws Exception {
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS,
                App.biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).stream()
                .filter(item -> item.getClass() == Book.class)
                .collect(Collectors.toCollection(ArrayList::new)));
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/show/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldShowMovieAvailableWhenMovieParam() throws Exception {
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS,
                App.biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).stream()
                .filter(item -> item.getClass() == Movie.class)
                .collect(Collectors.toCollection(ArrayList::new)));
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/show/movie"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldShowBookAvailableWhenBookAndAvailableParams() throws Exception {
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS,
                App.biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).stream()
                .filter(item -> item.getClass() == Book.class)
                .collect(Collectors.toCollection(ArrayList::new)));
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/show/book/available"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldShowBookNotAvailableWhenBookAndNotAvailableParams() throws Exception {
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS,
                App.biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE).stream()
                .filter(item -> item.getClass() == Book.class)
                .collect(Collectors.toCollection(ArrayList::new)));
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/show/book/not_available"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldShowBookAllWhenBookAndAllParams() throws Exception {
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS,
                App.biblioteca.getItems(Biblioteca.FILTER.ALL).stream()
                .filter(item -> item.getClass() == Book.class)
                .collect(Collectors.toCollection(ArrayList::new)));
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/show/book/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldShowMovieAvailableWhenMovieAndAvailableParams() throws Exception {
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS,
                App.biblioteca.getItems(Biblioteca.FILTER.AVAILABLE).stream()
                .filter(item -> item.getClass() == Movie.class)
                .collect(Collectors.toCollection(ArrayList::new)));
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/show/movie/available"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldShowMovieNotAvailableWhenMovieAndNotAvailableParams() throws Exception {
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS,
                App.biblioteca.getItems(Biblioteca.FILTER.NOT_AVAILABLE).stream()
                .filter(item -> item.getClass() == Movie.class)
                .collect(Collectors.toCollection(ArrayList::new)));
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/show/movie/not_available"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void shouldShowMovieAllWhenMovieAndAllParams() throws Exception {
        RestResponse expectedResult = new RestResponse(RestResponse.STATUS.SUCCESS,
                App.biblioteca.getItems(Biblioteca.FILTER.ALL).stream()
                .filter(item -> item.getClass() == Movie.class)
                .collect(Collectors.toCollection(ArrayList::new)));
        String json = itemJson.write(expectedResult).getJson();
        this.mockMvc.perform(get("/show/movie/available"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }
}
