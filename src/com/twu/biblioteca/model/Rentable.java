package com.twu.biblioteca.model;

public abstract  class Rentable {

    protected String title;
    protected boolean available;

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return available;
    }

    public void doCheckOut() {
        this.available = false;
    }

    public void doReturn() {
        this.available = true;
    }
}
