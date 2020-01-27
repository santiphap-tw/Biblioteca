package com.twu.biblioteca.model;

public interface RunnableWithParameter extends Runnable {
    void run(String parameter);
}