package com.twu.biblioteca.controller;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RestResponse;
import com.twu.biblioteca.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public RestResponse logout() {
        User user = Biblioteca.getInstance().user().logout();
        boolean isSuccess = user != null;
        if(isSuccess)
            return new RestResponse(RestResponse.STATUS.SUCCESS, Label.LOGOUT_SUCCESS.text + user.getName());
        else
            return new RestResponse(RestResponse.STATUS.FAIL, Label.MY_INFO_FAIL.text);
    }
}
