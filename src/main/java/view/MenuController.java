package view;

import java.util.Scanner;

public class MenuController {
    private final Scanner scanner;

    public MenuController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void showMenu() {
        // Implement a menu to show the user options
        // (1. use sql, 2. use file)
        // use file -> (1. get all reports, 2. get countries with maximal pollution
        //                                      [when the method in CsvDataReader will be ready])
        // use sql -> (1. Get countries with maximum pollution, 2. Get countries with minimal pollution, ...)
        // Implement menu navigation only for now.
    }
}
