package com.twu.biblioteca.model;

public abstract  class Rentable {

    protected String title;
    protected boolean available;
    protected User loaner = null;

    public String getTitle() {
        return title;
    }

    public boolean isAvailable() {
        return available;
    }

    public void doCheckOut(User loaner) {
        this.loaner = loaner;
        this.available = false;
        loaner.checkOutItem(this);
    }

    public void doReturn() {
        this.loaner.returnItem(this);
        this.loaner = null;
        this.available = true;
    }

    public User getLoaner() {
        return loaner;
    }
}
