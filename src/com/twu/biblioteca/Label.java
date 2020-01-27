package com.twu.biblioteca;

public enum Label {

    OPTION_EXIT("Exit the application"),
    OPTION_EXIT_COMMAND("exit"),
    OPTION_INVALID("Please select a valid option!"),
    OPTION_SHOW_BOOKS("Show the list of books, can be followed with \"show available|not available|all\". Default is available."),
    OPTION_SHOW_BOOKS_COMMAND("show"),
    OPTION_CHECKOUT("Checkout the book, ie. \"checkout BOOK_NAME\""),
    OPTION_CHECKOUT_COMMAND("checkout"),
    OPTION_RETURN("Return the book, ie. \"return BOOK_NAME\""),
    OPTION_RETURN_COMMAND("return"),
    OPTION_PROMPT("What would you like to do?"),
    OPTION_INPUT_PROMPT(">>> "),
    CHECKOUT_SUCCESS("Thank you! Enjoy the book"),
    CHECKOUT_FAIL("Sorry, that book is not available"),
    RETURN_SUCCESS("Thank you for returning the book"),
    RETURN_FAIL("This is not a valid book to return"),
    BOOK_NAME_PROMPT("Enter the book name:"),
    WELCOME("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bangalore!"),
    EXIT("Thank you for using Biblioteca!");

    public final String text;

    private Label(String label) {
        this.text = label;
    }
}
