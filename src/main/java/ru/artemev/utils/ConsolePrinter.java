package ru.artemev.utils;

import java.util.Optional;
import java.util.Scanner;

public class ConsolePrinter {

    private static final String BANNER_PATH = "banner";

    private static final String GREEN = "\u001b[32m";
    private static final String RED = "\u001b[31m";
    private static final String RESET = "\u001B[0m";
    private static final String INPUT = String.format("[%sInput%s] -> ", GREEN, RESET);

    private final Scanner scanner = new Scanner(System.in);

    public String wrapperInput() {
        System.out.printf(INPUT);
        return scanner.nextLine();
    }

    public void printBannerAndGreetings() {
        Optional.ofNullable(FileHelper.getContentFromResource(BANNER_PATH))
                .ifPresent(System.out::println);

        System.out.println("Дарова! Чего хотим качнуть?");
    }

}
