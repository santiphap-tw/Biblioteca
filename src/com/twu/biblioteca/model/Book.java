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

    public String header (boolean showBorrower) {
        String header = "------- Book List -------\n";
        header += "Title\t|\tAuthor\t|\tPublish Date";
        if(showBorrower) header += "\t|\tBorrower";
        return header;
    }

    public String info(boolean showBorrower) {
        String bookInfo = this.getTitle() + "\t|\t" + this.getAuthor() + "\t|\t" + this.getPublishDate();
        if(showBorrower) bookInfo += this.getBorrower() != null ? "\t|\t" + this.getBorrower().getName() : "\t|\t-";
        return bookInfo;
    }
}
