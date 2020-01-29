package com.twu.biblioteca.model;

public class Movie extends Rentable {

    private int year;
    private String director;
    private int rating;

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

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public int getRating() {
        return rating;
    }

    public String header (boolean showBorrower) {
        String header = "------- Movies List -------\n";
        header += "Title\t|\tYear\t|\tDirector\t|\tRating";
        if(showBorrower) header += "\t|\tBorrower";
        return header;
    }

    public String info(boolean showBorrower) {
        String movieInfo = this.getTitle() + "\t|\t" + this.getYear() + "\t|\t" + this.getDirector() + "\t|\t" + this.getRating();
        if(showBorrower) movieInfo += this.getBorrower() != null ? "\t|\t" + this.getBorrower().getName() : "\t|\t-";
        return movieInfo;
    }
}
