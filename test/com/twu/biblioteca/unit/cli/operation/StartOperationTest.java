package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.cli.operation.StartOperation;
import com.twu.biblioteca.model.Label;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StartOperationTest {

    @Test
    public void shouldShowWelcomeAndOptionPrompt() {
        // Given
        StartOperation startOperation = new StartOperation("", null);
        // When
        ArrayList<String> output = startOperation.run("");
        // Then
        boolean isWelcomeText = output.stream().anyMatch(text -> text.equals(Label.WELCOME.text));
        boolean isOptionPromptText = output.stream().anyMatch(text -> text.equals(Label.OPTION_PROMPT.text));
        assertEquals("Start should show welcome & option prompt text", true, isWelcomeText & isOptionPromptText);
    }
}
