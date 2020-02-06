package com.twu.biblioteca.controller;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RestResponse;
import com.twu.biblioteca.model.request.ItemRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckOutController {

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public RestResponse doCheckOut(@RequestBody ItemRequest item) {
        Biblioteca.RESPONSE response = Biblioteca.getInstance().doCheckOut(item.getName());
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
