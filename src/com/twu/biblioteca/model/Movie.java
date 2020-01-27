package com.twu.biblioteca.model;

public class Movie {
    private String title;
    private int year;
    private String director;
    private int rating;
    private boolean available;

    public Movie(String title) {
        this(title,0,"Unknown",0);
    }

    public Movie(String title, int year, String director, int rating) {
        this.title = title;
        this.year = year;
        this.director = director;
        this.rating = rating;
        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public int getRating() {
        return rating;
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
