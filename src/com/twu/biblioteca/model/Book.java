package com.twu.biblioteca.model;

public class Book extends Rentable {

    private String author;
    private String publishDate;

    public Book(String title) {
        this(title, "", "");
    }

    public Book(String title, String author, String publishDate) {
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
        this.available = true;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishDate() {
        return publishDate;
    }
}
