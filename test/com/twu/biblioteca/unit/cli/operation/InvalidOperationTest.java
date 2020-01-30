package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.cli.BibliotecaApp;
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
        // Given
        invalidOperation = new InvalidOperation("");
    }

    @Test
    public void invalidTest() {
        // When
        ArrayList<String> output = invalidOperation.run("");
        // Then
        boolean isInvalid = output.stream().anyMatch(text -> text.equals(Label.OPTION_INVALID.text));
        isInvalid = isInvalid & invalidOperation.getResponse().equals(BibliotecaApp.RESPONSE.INVALID);
        assertEquals("Invalid operation should invalid", true, isInvalid);
    }
}
