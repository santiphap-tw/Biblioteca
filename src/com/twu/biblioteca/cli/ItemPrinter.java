package com.twu.biblioteca.cli;

import com.twu.biblioteca.model.Book;
import com.twu.biblioteca.model.Movie;
import com.twu.biblioteca.model.Rental;

import java.util.ArrayList;
import java.util.Collections;

public class ItemPrinter {

    public static ArrayList<String> header(Class<? extends Rental> itemType, boolean isShowBorrower) {
        ArrayList<String> output = new ArrayList<String>();
        if(itemType == Book.class){
            output.add("------- Books List -------");
            output.add("Title\t|\tAuthor\t|\tPublish Date" + (isShowBorrower ? "\t|\tBorrower" : ""));
        }
        else if(itemType == Movie.class){
            output.add("------- Movies List -------");
            output.add("Title\t|\tYear\t|\tDirector\t|\tRating" + (isShowBorrower ? "\t|\tBorrower" : ""));
        }
        else {
            output.add("------- Items List -------");
            output.add("Title\t|\tType" + (isShowBorrower ? "\t|\tBorrower" : ""));
        }
        return output;
    }

    public static ArrayList<String> item(Rental item, boolean isShowBorrower) {
        ArrayList<String> output = new ArrayList<String>();
        if(item.getClass() == Book.class){
            Book book = (Book) item;
            output.add(book.getTitle() + "\t|\t" + book.getAuthor() + "\t|\t" + book.getPublishDate() + (isShowBorrower ? book.getBorrower() != null ? "\t|\t" + book.getBorrower().getName() : "\t|\t-" : ""));
        }
        else if(item.getClass() == Movie.class){
            Movie movie = (Movie) item;
            output.add(movie.getTitle() + "\t|\t" + movie.getYear() + "\t|\t" + movie.getDirector() + "\t|\t" + movie.getRating() + (isShowBorrower ? movie.getBorrower() != null ? "\t|\t" + movie.getBorrower().getName() : "\t|\t-" : ""));
        }
        else {
            output.add(item.getTitle() + "\t|\t" + item.getClass().getName() + (isShowBorrower ? item.getBorrower() != null ? "\t|\t" + item.getBorrower().getName() : "\t|\t-" : ""));
        }
        return output;
    }

    public static ArrayList<String> collection(ArrayList<Rental> items,Class<? extends Rental> itemType, boolean isShowBorrower) {
        ArrayList<String> output = new ArrayList<String>();
        ArrayList<Class<? extends Rental>> isHeaderPrinted = new ArrayList<Class<? extends Rental>>();
        Collections.sort(items, (o1, o2) -> o1.getClass().getName().compareTo(o2.getClass().getName()));
        items.stream()
                .filter(item -> item.getClass() == itemType || itemType == Rental.class)
                .forEach(item -> {
                    // Check if header of this item type was already printed or not
                    boolean isAlreadyPrintHeader = isHeaderPrinted.contains(item.getClass());
                    if(!isAlreadyPrintHeader) {
                        output.addAll(ItemPrinter.header(item.getClass(), isShowBorrower));
                        isHeaderPrinted.add(item.getClass());
                    }
                    output.addAll(ItemPrinter.item(item, isShowBorrower));
        });
        return output;
    }
}
