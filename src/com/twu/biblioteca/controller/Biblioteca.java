package com.twu.biblioteca.controller;

import com.twu.biblioteca.controller.runnable.OptionRunnable;
import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Label;

import java.util.*;

public class Biblioteca {

    public enum BOOK_FILTER {
        AVAILABLE,
        NOT_AVAILABLE,
        ALL
    }

    private ArrayList<Book> books;

    public Biblioteca() {
        books = new ArrayList<Book>();
        addDefaultBooks();
    }

    public ArrayList<Book> getBooks(BOOK_FILTER bookFilter) {
        ArrayList<Book> books = new ArrayList<Book>();
        for(Book book : this.books){
            switch (bookFilter){
                case AVAILABLE:
                    if(book.isAvailable()) books.add(book);
                    break;
                case NOT_AVAILABLE:
                    if(!book.isAvailable()) books.add(book);
                    break;
                case ALL:
                    books.add(book);
                    break;
            }
        }
        return books;
    }

    public boolean doCheckOut(String bookName) {
        for(Book book : this.books) {
            if(bookName.equals(book.getTitle())){
                if(!book.isAvailable()) return false;
                book.doCheckOut();
                return true;
            }
        }
        return false;
    }

    public boolean doReturn(String bookName) {
        for(Book book : this.books) {
            if(bookName.equals(book.getTitle())){
                if(book.isAvailable()) return false;
                book.doReturn();
                return true;
            }
        }
        return false;
    }

    private void addDefaultBooks(){
        books.add(new Book("Book A", "Santiphap A.", "01/01/2008"));
        books.add(new Book("Book B", "Santiphap B.", "02/01/2008"));
        books.add(new Book("Book C", "Santiphap C.", "03/01/2008"));
    }
}
