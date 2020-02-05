package com.twu.biblioteca.rest;

import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RestResponse;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class WelcomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public RestResponse index() {
        return new RestResponse(RestResponse.STATUS.SUCCESS, Label.WELCOME.text);
    }

}