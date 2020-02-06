package com.twu.biblioteca.controller;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RestResponse;
import com.twu.biblioteca.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestResponse login(@RequestParam String id, @RequestParam String password) {
        User user = Biblioteca.getInstance().user().login(id,password);
        boolean isSuccess = user != null;
        if(isSuccess)
            return new RestResponse(RestResponse.STATUS.SUCCESS, Label.LOGIN_SUCCESS.text + user.getName());
        else
            return new RestResponse(RestResponse.STATUS.FAIL, Label.LOGIN_FAIL.text);
    }
}
