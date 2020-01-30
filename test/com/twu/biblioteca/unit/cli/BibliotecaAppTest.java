package com.twu.biblioteca.unit.cli;

import com.twu.biblioteca.cli.BibliotecaApp;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BibliotecaAppTest {
    @Test
    public void startTest() {
        // start() test is testing in BibliotecaAppCommandTest
    }

    @Test
    public void selectOptionTest() {
        // selectOption() test is testing in BibliotecaAppCommandTest
    }

    @Test
    public void printOutputTest() {
        // Before - Setup I/O
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));
        // Given
        ArrayList<String> output = new ArrayList<String>();
        output.add("test line 1");
        output.add("test line 2");
        // When
        BibliotecaApp.printOutput(output);
        // Then
        assertEquals("printOutput should print same as input", "test line 1\ntest line 2\n", outContent.toString());
        // After - Setup I/O
        System.setOut(originalOut);
    }
}
