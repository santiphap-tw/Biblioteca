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
        this.borrower.returnItem(this);
        this.borrower = null;
        this.available = true;
    }

    public User getBorrower() {
        return borrower;
    }

    public String header (boolean showBorrower) {
        String header = "------- Item List -------\n";
        header += "Title\t|\tType";
        if(showBorrower) header += "\t|\tBorrower";
        return header;
    }

    public String info(boolean showBorrower) {
        String itemInfo = this.getTitle() + "\t|\t" + this.getClass().getName();
        if(showBorrower) itemInfo += this.getBorrower() != null ? "\t|\t" + this.getBorrower().getName() : "\t|\t-";
        return itemInfo;
    }
}
