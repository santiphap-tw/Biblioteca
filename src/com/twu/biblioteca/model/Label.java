package com.twu.biblioteca.model;

public enum Label {

    OPTION_EXIT("Exit the application"),
    OPTION_EXIT_COMMAND("exit"),
    OPTION_INVALID("Please select a valid option!"),
    OPTION_SHOW_ALL("Show the list of items, can be followed with \"show available|not available|all\". Default is available."),
    OPTION_SHOW_ALL_COMMAND("show"),
    OPTION_SHOW_BOOKS("Show the list of books, can be followed with \"show_book available|not available|all\". Default is available."),
    OPTION_SHOW_BOOKS_COMMAND("show_book"),
    OPTION_SHOW_MOVIES("Show the list of movies, can be followed with \"show_movie available|not available|all\". Default is available."),
    OPTION_SHOW_MOVIES_COMMAND("show_movie"),
    OPTION_CHECKOUT("Checkout the item, ie. \"checkout ITEM_NAME\""),
    OPTION_CHECKOUT_COMMAND("checkout"),
    OPTION_RETURN("Return the item, ie. \"return ITEM_NAME\""),
    OPTION_RETURN_COMMAND("return"),
    OPTION_LOGIN("Login to Biblioteca, ie. \"login ID PASSWORD\""),
    OPTION_LOGIN_COMMAND("login"),
    OPTION_LOGOUT("Logout from Biblioteca"),
    OPTION_LOGOUT_COMMAND("logout"),
    OPTION_HELP("Show commands list"),
    OPTION_HELP_COMMAND("help"),
    OPTION_MY_INFO("Show user information"),
    OPTION_MY_INFO_COMMAND("whoami"),
    OPTION_MY_BORROWING("Show items you borrowed."),
    OPTION_MY_BORROWING_COMMAND("borrowing"),
    OPTION_PROMPT("What would you like to do?"),
    OPTION_INPUT_PROMPT(">>> "),
    LOGIN_SUCCESS("Welcome, "),
    LOGIN_FAIL("Sorry, your id or password is incorrect"),
    LOGOUT_SUCCESS("Goodbye, "),
    CHECKOUT_SUCCESS("Thank you! Enjoy the content"),
    CHECKOUT_FAIL("Sorry, that item is not available"),
    RETURN_SUCCESS("Thank you for returning"),
    RETURN_FAIL("This is not a valid item to return"),
    MY_INFO_FAIL("You are not logged in. Please login."),
    AUTHORIZATION_ERROR("You don't have an authorization, please re-login."),
    WELCOME("Welcome to Biblioteca. Your one-stop-shop for great book & movie titles in Bangalore!"),
    EXIT("Thank you for using Biblioteca!");

    public final String text;

    private Label(String label) {
        this.text = label;
    }
}
