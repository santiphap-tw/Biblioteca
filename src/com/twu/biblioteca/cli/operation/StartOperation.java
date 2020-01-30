package com.twu.biblioteca.cli.operation;

import com.twu.biblioteca.cli.BibliotecaApp;
import com.twu.biblioteca.model.AppOperation;
import com.twu.biblioteca.model.Label;

import java.util.ArrayList;
import java.util.Map;

public class StartOperation extends AppOperation {

    private Map<String, AppOperation> options;

    public StartOperation(String description, Map<String, AppOperation> options) {
        super(description);
        this.options = options;
    }

    @Override
    public ArrayList<String> run(String parameter) {
        ArrayList<String> output = new ArrayList<String>();

        output.add(Label.WELCOME.text);
        output.add(Label.OPTION_PROMPT.text);
        if(options != null)
            options.forEach((command, option) -> {
                output.add(command+ "\t\t" + option.getDescription());
            });

        response = BibliotecaApp.RESPONSE.VALID;
        return output;
    }
}
