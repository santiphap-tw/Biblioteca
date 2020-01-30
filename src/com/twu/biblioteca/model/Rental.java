package com.twu.biblioteca.model;

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

    public User getBorrower() {
        return borrower;
    }
}
