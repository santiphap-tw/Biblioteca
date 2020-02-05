package com.twu.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Arrays;
import java.util.LinkedHashMap;

public abstract  class Rental {

    protected String title;
    protected boolean available;
    protected User borrower = null;

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return available;
    }

    public void doCheckOut(User borrower) {
        this.borrower = borrower;
        this.available = false;
        borrower.checkOutItem(this);
    }

    public void doReturn() {
        if(this.borrower != null) this.borrower.returnItem(this);
        this.borrower = null;
        this.available = true;
    }

    @JsonBackReference
    public User getBorrower() {
        return borrower;
    }

    public LinkedHashMap<String, String> attributes() {
        LinkedHashMap<String, String> attributes = new LinkedHashMap<>();
        attributes.put("Title", getTitle());
        Arrays.stream(this.getClass().getMethods())
                .filter(method -> method.getName().matches("get.*") && method.getName() != "getClass")
                .forEach(method -> {
                    try {
                        String output = method.invoke(this).toString();
                        output = output.equals("") ? "-" : output;
                        attributes.put(method.getName().substring(3), output);
                    } catch (Exception e) {
                        attributes.put(method.getName().substring(3), "-");
                    }
                });
        return attributes;
    }
}
