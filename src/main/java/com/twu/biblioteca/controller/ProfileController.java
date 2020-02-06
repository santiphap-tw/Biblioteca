package com.twu.biblioteca.controller;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RestResponse;
import com.twu.biblioteca.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public RestResponse logout() {
        User user = Biblioteca.getInstance().user().getCurrentUser();
        boolean isLogin = user != null;
        if(isLogin)
            return new RestResponse(RestResponse.STATUS.SUCCESS, user);
        else
            return new RestResponse(RestResponse.STATUS.FAIL, Label.MY_INFO_FAIL.text);
    }
}