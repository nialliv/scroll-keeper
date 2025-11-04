package ru.artemev.services.impl;

import org.apache.commons.lang3.Strings;
import ru.artemev.services.PrinterService;
import ru.artemev.services.downloaders.Downloader;
import ru.artemev.utils.FileHelper;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PrinterServiceImpl implements PrinterService {

    private static final String BANNER_PATH = "banner";

    private static final String GREEN = "\u001b[32m";
    private static final String RED = "\u001b[31m";
    private static final String RESET = "\u001B[0m";
    private static final String INPUT = String.format("[%sInput%s] -> ", GREEN, RESET);
    private static final String YES_OR_NOT = String.format("[%sY%s/%sn%s] -> ", GREEN, RESET, RED, RESET);

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void printBannerAndGreetings() {
        Optional.ofNullable(FileHelper.getContentFromResource(BANNER_PATH))
                .ifPresent(System.out::println);

        System.out.println("Дарова! Чего хотим качнуть?");
    }

    @Override
    public String wrapperInput() {
        System.out.printf(INPUT);
        return scanner.nextLine();
    }

    @Override
    public boolean wrongAnswerGetAnother() {
        System.out.printf("Ты ввел ересь... го по новой?\n%s", YES_OR_NOT);
        return Strings.CI.equals("Y", scanner.nextLine());
    }

    @Override
    public void error(Exception e) {
        System.out.printf("Дядя, у нас какая-то хрень случилась... Error - %s\n", e.getMessage());
    }

    @Override
    public void println(String message) {
        System.out.println(message);
    }

    @Override
    public void printAvailableDownloaderInfo(List<Downloader> downloaders) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Доступные загрузчики:");
        for (int i = 0; i < downloaders.size(); i++) {
            stringBuilder.append("\n\t");
            stringBuilder.append(i + 1);
            stringBuilder.append(" - ");
            stringBuilder.append(downloaders.get(i).getInfo());
        }
        System.out.println(stringBuilder);
    }
}
