package com.twu.biblioteca.controller;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.model.Label;
import com.twu.biblioteca.model.RestResponse;
import com.twu.biblioteca.model.request.ItemRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReturnController {

    @RequestMapping(value = "/return", method = RequestMethod.POST)
    public RestResponse doReturn(@RequestBody ItemRequest item) {
        Biblioteca.RESPONSE response = Biblioteca.getInstance().doReturn(item.getName());
        switch (response) {
            case SUCCESS:
                return new RestResponse(RestResponse.STATUS.SUCCESS, Label.RETURN_SUCCESS.text);
            case DEFAULT_ERROR:
                return new RestResponse(RestResponse.STATUS.FAIL, Label.RETURN_FAIL.text);
            case AUTHORIZATION_ERROR:
                return new RestResponse(RestResponse.STATUS.FAIL, Label.AUTHORIZATION_ERROR.text);
        }
        return new RestResponse(RestResponse.STATUS.FAIL, null);
    }
}
