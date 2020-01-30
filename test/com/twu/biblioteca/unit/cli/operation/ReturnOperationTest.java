package com.twu.biblioteca.unit.cli.operation;

import com.twu.biblioteca.Biblioteca;
import com.twu.biblioteca.cli.operation.ReturnOperation;
import com.twu.biblioteca.model.Label;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ReturnOperationTest {

    private Biblioteca biblioteca;
    private ReturnOperation returnOperation;

    @Before
    public void initialize(){
        biblioteca = new Biblioteca();
        returnOperation = new ReturnOperation("", biblioteca);
        biblioteca.user().login("111-1111", "1111");
        biblioteca.doCheckOut("Book A");
        biblioteca.doCheckOut("Movie A");
    }

    @Test
    public void returnTest() {
        // Setup operation
        ArrayList<String> output;

        // Positive test
        output = returnOperation.run("Book A");
        boolean isSuccess = output.stream().anyMatch(text -> text.equals(Label.RETURN_SUCCESS.text));
        assertEquals("return should be success", true, isSuccess);

        // Negative test - Not available item
        output = returnOperation.run("Book A");
        boolean isFailWhenNotAvailable = output.stream().anyMatch(text -> text.equals(Label.RETURN_FAIL.text));
        assertEquals("return should be fail", true, isFailWhenNotAvailable);
        output = returnOperation.run("Book Z");
        boolean isFailWhenIncorrectName = output.stream().anyMatch(text -> text.equals(Label.RETURN_FAIL.text));
        assertEquals("return should be fail", true, isFailWhenIncorrectName);

        // Negative test - Authorization Error
        biblioteca.user().logout();
        output = returnOperation.run("Movie A");
        boolean isFailWhenNotLoggedIn = output.stream().anyMatch(text -> text.equals(Label.AUTHORIZATION_ERROR.text));
        assertEquals("return should be fail", true, isFailWhenNotLoggedIn);
        biblioteca.user().login("222-2222", "2222");
        output = returnOperation.run("Movie A");
        boolean isFailWhenWrongUser = output.stream().anyMatch(text -> text.equals(Label.AUTHORIZATION_ERROR.text));
        assertEquals("return should be fail", true, isFailWhenWrongUser);
    }
}
