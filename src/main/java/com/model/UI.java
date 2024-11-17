package com.model;

import java.util.Scanner;

public class UI {

    /**
     * Gets user input from Scanner
     * 
     * @return input
     */
    public static String getInput() {
        Scanner keyboard = new Scanner(System.in);
        String input = keyboard.next();
        keyboard.close();
        return input;
    }
}
