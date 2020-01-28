package com.twu.biblioteca;

import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Movie;
import com.twu.biblioteca.model.Rentable;

import java.util.*;

public class Biblioteca {

    public enum FILTER {
        AVAILABLE,
        NOT_AVAILABLE,
        ALL
    }

    private ArrayList<Rentable> items;

    public Biblioteca() {
        items = new ArrayList<Rentable>();
        addDefaultBooks();
        addDefaultMovies();
    }

    public ArrayList<Rentable> getItems(FILTER filter) {
        ArrayList<Rentable> items = new ArrayList<Rentable>();
        for(Rentable item : this.items){
            switch (filter){
                case AVAILABLE:
                    if(item.isAvailable()) items.add(item);
                    break;
                case NOT_AVAILABLE:
                    if(!item.isAvailable()) items.add(item);
                    break;
                case ALL:
                    items.add(item);
                    break;
            }
        }
        return items;
    }

    public boolean doCheckOut(String itemName) {
        for(Rentable item : this.items) {
            if(itemName.equals(item.getTitle())){
                if(!item.isAvailable()) return false;
                item.doCheckOut();
                return true;
            }
        }
        return false;
    }

    public boolean doReturn(String itemName) {
        for(Rentable item : this.items) {
            if(itemName.equals(item.getTitle())){
                if(item.isAvailable()) return false;
                item.doReturn();
                return true;
            }
        }
        return false;
    }

    private void addDefaultBooks(){
        items.add(new Book("Book A", "Santiphap A.", "01/01/2008"));
        items.add(new Book("Book B", "Santiphap B.", "02/01/2008"));
        items.add(new Book("Book C", "Santiphap C.", "03/01/2008"));
    }

    private void addDefaultMovies(){
        items.add(new Movie("Movie A", 2008, "Santiphap A.", 8));
        items.add(new Movie("Movie B", 2013,"Santiphap B.", 9));
        items.add(new Movie("Movie C", 2020, "Santiphap C.", 10));
    }
}
