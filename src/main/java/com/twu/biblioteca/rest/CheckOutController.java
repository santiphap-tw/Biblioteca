package com.twu.biblioteca.rest;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RestResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckOutController {

    @RequestMapping("/checkout/{name}")
    public RestResponse doCheckOut(@PathVariable String name) {
        Biblioteca.RESPONSE response = Biblioteca.getInstance().doCheckOut(name);
        switch (response) {
            case SUCCESS:
                return new RestResponse(RestResponse.STATUS.SUCCESS, Label.CHECKOUT_SUCCESS.text);
            case DEFAULT_ERROR:
                return new RestResponse(RestResponse.STATUS.FAIL, Label.CHECKOUT_FAIL.text);
            case AUTHORIZATION_ERROR:
                return new RestResponse(RestResponse.STATUS.FAIL, Label.AUTHORIZATION_ERROR.text);
        }
        return new RestResponse(RestResponse.STATUS.FAIL, null);
    }
}
