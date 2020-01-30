package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.cli.operation.ExitOperation;
import com.twu.biblioteca.cli.operation.InvalidOperation;
import com.twu.biblioteca.model.Label;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class InvalidOperationTest {

    private InvalidOperation invalidOperation;

    @Before
    public void initialize(){
        invalidOperation = new InvalidOperation("");
    }

    @Test
    public void invalidTest() {
        ArrayList<String> output = invalidOperation.run("");
        boolean isInvalid = output.stream().anyMatch(text -> text.equals(Label.OPTION_INVALID.text));
        isInvalid = isInvalid & invalidOperation.getResponse().equals(BibliotecaApp.RESPONSE.INVALID);
        assertEquals("Invalid operation should invalid", true, isInvalid);
    }
}
