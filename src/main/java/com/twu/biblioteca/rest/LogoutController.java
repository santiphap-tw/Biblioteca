package com.twu.biblioteca.rest;

import com.twu.biblioteca.WebApp;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RestResponse;
import com.twu.biblioteca.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogoutController {

    @RequestMapping("/logout")
    public RestResponse logout() {
        User user = WebApp.biblioteca.user().logout();
        boolean isSuccess = user != null;
        if(isSuccess)
            return new RestResponse(RestResponse.STATUS.SUCCESS, Label.LOGOUT_SUCCESS.text + user.getName());
        else
            return new RestResponse(RestResponse.STATUS.FAIL, Label.MY_INFO_FAIL.text);
    }
}
