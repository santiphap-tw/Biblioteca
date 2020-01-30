package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.cli.operation.CheckOutOperation;
import com.twu.biblioteca.cli.operation.ExitOperation;
import com.twu.biblioteca.model.Label;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ExitOperationTest {

    private ExitOperation exitOperation;

    @Before
    public void initialize(){
        exitOperation = new ExitOperation("");
    }

    @Test
    public void exitTest() {
        ArrayList<String> output = exitOperation.run("");
        boolean isExit = output.stream().anyMatch(text -> text.equals(Label.EXIT.text));
        isExit = isExit & exitOperation.getResponse().equals(BibliotecaApp.RESPONSE.EXIT);
        assertEquals("Exit operation should exit", true, isExit);
    }
}
