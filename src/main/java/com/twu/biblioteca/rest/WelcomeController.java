package com.twu.biblioteca.rest;

import com.twu.biblioteca.model.Label;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class WelcomeController {

    @RequestMapping("/")
    public String index() {
        return Label.WELCOME.text;
    }

}