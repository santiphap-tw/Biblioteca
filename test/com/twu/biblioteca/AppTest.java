package com.twu.biblioteca;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AppTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void bibliotecaShouldHaveWelcomeMessage() {
        new BibliotecaApp();
        String[] outputLine = outContent.toString().split("\n");
        String firstLine = outputLine[0];
        assertEquals("Biblioteca should have a welcome message",true, firstLine.toLowerCase().contains("welcome"));
    }
}
