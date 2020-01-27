package com.twu.biblioteca.controller.runnable;

public interface RunnableWithParameter extends Runnable {
    void run(String parameter);
}