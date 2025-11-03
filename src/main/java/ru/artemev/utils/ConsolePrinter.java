package ru.artemev.utils;

import org.apache.commons.lang3.Strings;
import ru.artemev.dto.ServiceDescription;
import ru.artemev.services.Service;
import ru.artemev.services.impl.ShadowSlaveService;

import java.util.Optional;
import java.util.Scanner;

public class ConsolePrinter {

    private static final String BANNER_PATH = "banner";

    private static final String GREEN = "\u001b[32m";
    private static final String RED = "\u001b[31m";
    private static final String RESET = "\u001B[0m";
    private static final String INPUT = String.format("[%sInput%s] -> ", GREEN, RESET);
    private static final String YES_OR_NOT = String.format("[%sY%s/%sn%s] -> ", GREEN, RESET, RED, RESET);

    private final Scanner scanner = new Scanner(System.in);

    public void printBannerAndGreetings() {
        Optional.ofNullable(FileHelper.getContentFromResource(BANNER_PATH))
                .ifPresent(System.out::println);

        System.out.println("Дарова! Чего хотим качнуть?");
    }

    public String wrapperInput() {
        System.out.printf(INPUT);
        return scanner.nextLine();
    }

    public boolean wrongAnswerGetAnother() {
        System.out.printf("Ты ввел ересь... го по новой?\n%s", YES_OR_NOT);
        return Strings.CI.equals("Y", scanner.nextLine());
    }

    public void error(Exception e) {
        System.out.printf("Дядя, у нас какая-то хрень случилась... Error - %s\n", e.getMessage());
    }

    public void getInfoByService(Service clazz) {
        System.out.println(ServiceDescription.getDescriptionByService());
    }

    public void printAvailableDownloaders(Service se) {

    }
}