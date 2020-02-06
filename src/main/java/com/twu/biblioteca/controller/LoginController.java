package com.twu.biblioteca.controller;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RestResponse;
import com.twu.biblioteca.model.User;
import com.twu.biblioteca.model.request.UserRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestResponse login(@RequestBody UserRequest userRequest) {
        User user = Biblioteca.getInstance().user().login(userRequest.getId(), userRequest.getPassword());
        boolean isSuccess = user != null;
        if(isSuccess)
            return new RestResponse(RestResponse.STATUS.SUCCESS, Label.LOGIN_SUCCESS.text + user.getName());
        else
            return new RestResponse(RestResponse.STATUS.FAIL, Label.LOGIN_FAIL.text);
    }
}
