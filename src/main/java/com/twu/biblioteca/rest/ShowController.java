package com.twu.biblioteca.rest;

import com.twu.biblioteca.database.RentalDatabase;
import com.twu.biblioteca.model.Rental;
import com.twu.biblioteca.model.RestResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
public class ShowController {

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public RestResponse showNoParam() {
        return showTwoParams("rental", "available");
    }

    @RequestMapping(value = "/show/{parameter}", method = RequestMethod.GET)
    public RestResponse showOneParam(@PathVariable String parameter) {
        switch (parameter){
            case "available":
            case "not_available":
            case "all":
                // Parameter is defining filter
                return showTwoParams("rental", parameter);
            default:
                // Parameter is defining type
                return showTwoParams(parameter, "available");
        }
    }

    @RequestMapping(value = "/show/{type}/{filter}", method = RequestMethod.GET)
    public RestResponse showTwoParams(@PathVariable String type, @PathVariable String filter) {
        switch (filter){
            case "available":
                return new RestResponse(RestResponse.STATUS.SUCCESS,
                        RentalDatabase.getInstance().getItems(RentalDatabase.Filter.AVAILABLE).stream()
                        .filter(item -> isSameType(item,type))
                        .collect(Collectors.toCollection(ArrayList::new)));
            case "not_available":
                return new RestResponse(RestResponse.STATUS.SUCCESS,
                        RentalDatabase.getInstance().getItems(RentalDatabase.Filter.NOT_AVAILABLE).stream()
                        .filter(item -> isSameType(item,type))
                        .collect(Collectors.toCollection(ArrayList::new)));
            case "all":
                return new RestResponse(RestResponse.STATUS.SUCCESS,
                        RentalDatabase.getInstance().getItems(RentalDatabase.Filter.ALL).stream()
                        .filter(item -> isSameType(item,type))
                        .collect(Collectors.toCollection(ArrayList::new)));
            default:
                return new RestResponse(RestResponse.STATUS.FAIL,null);
        }
    }

    private boolean isSameType(Rental item, String type) {
        // If type is Rental (which is superclass), item is considered as a same type
        String rentalCassName = Rental.class.getSimpleName().toLowerCase();
        if(type.equals(rentalCassName)) return true;
        // Check if class name is equal to type
        String className = item.getClass().getSimpleName().toLowerCase();
        return type.equals(className);
    }

}
