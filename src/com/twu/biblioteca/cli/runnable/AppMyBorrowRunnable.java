package com.twu.biblioteca.cli.runnable;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.model.*;

import java.util.ArrayList;

public class AppMyBorrowRunnable implements RunnableWithParameter {

    private Biblioteca biblioteca;

    public AppMyBorrowRunnable(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    @Override
    public void run() {
        User user = biblioteca.getCurrentUser();
        boolean isLogin = user != null;
        if(isLogin){
            ArrayList<Book> books = new ArrayList<Book>();
            ArrayList<Movie> movies = new ArrayList<Movie>();
            for(Rentable item : user.getItems()){
                if(item.getClass() == Book.class) books.add((Book) item);
                if(item.getClass() == Movie.class) movies.add((Movie) item);
            }
            if(books.size() > 0) BibliotecaApp.printBooks(books,false);
            if(movies.size() > 0) BibliotecaApp.printMovie(movies, false);
        } else {
            System.out.println(Label.MY_INFO_FAIL.text);
        }
    }

    @Override
    public void run(String parameter) {
        run();
    }
}