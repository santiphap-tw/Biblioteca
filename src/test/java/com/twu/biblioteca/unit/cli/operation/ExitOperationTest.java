package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.cli.operation.ExitOperation;
import com.twu.biblioteca.model.Label;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ExitOperationTest {

    @Test
    public void shouldExit() {
        // Given
        ExitOperation exitOperation = new ExitOperation("");
        // When
        ArrayList<String> output = exitOperation.run("");
        // Then
        boolean isExit = output.stream().anyMatch(text -> text.equals(Label.EXIT.text));
        assertEquals("Exit operation should exit", true, isExit);
    }
}
