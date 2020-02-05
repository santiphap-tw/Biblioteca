package com.twu.biblioteca.cli;

import com.twu.biblioteca.model.Rental;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class Formatter {

    private static String separator = "\t|\t";

    public static ArrayList<String> items(ArrayList<Rental> items, Class<? extends Rental> itemType, boolean isShowBorrower) {
        if(itemType == Rental.class) {
            ArrayList<String> output = new ArrayList<String>();
            items.stream()
                    .map(Rental::getClass)
                    .distinct()
                    .forEach(type -> output.addAll(Formatter.items(items,type,isShowBorrower)));
            return output;
        }
        else {
            ArrayList<String> output = new ArrayList<String>();
            // Add Header
            output.add("<< " + itemType.getSimpleName() + "s List >>");
            items.stream()
                    .filter(item -> item.getClass() == itemType)
                    .findFirst()
                    .ifPresent(item -> {
                        output.add(item.attributes().keySet().stream()
                                .filter(header -> !header.equals("Borrower") || isShowBorrower)
                                .collect(Collectors.joining(separator)));
                    });
            // Add content
            items.stream()
                    .filter(item -> item.getClass() == itemType)
                    .forEach(item -> {
                        output.add(item.attributes().entrySet().stream()
                                .filter(entry -> !entry.getKey().equals("Borrower") || isShowBorrower)
                                .map(Map.Entry::getValue)
                                .collect(Collectors.joining(separator)));
                    });
            return output;
        }
    }
}
