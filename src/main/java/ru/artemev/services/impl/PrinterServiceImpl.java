package ru.artemev.services.impl;

import org.apache.commons.lang3.Strings;
import ru.artemev.dto.ContentRange;
import ru.artemev.dto.ErrorContent;
import ru.artemev.dto.PrintedDirectoriesContainer;
import ru.artemev.services.PrinterService;
import ru.artemev.services.downloaders.Source;
import ru.artemev.utils.FileHelper;
import ru.artemev.utils.RegexUtils;

import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
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
    private static final String Y = "Y";

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
        return Strings.CI.equals(Y, scanner.nextLine());
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
    public void printAvailableSourceInfo(List<Source> sources) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Доступные загрузчики:");
        for (int i = 0; i < sources.size(); i++) {
            stringBuilder.append("\n\t");
            stringBuilder.append(i + 1);
            stringBuilder.append(" - ");
            stringBuilder.append(sources.get(i).getInfo());
        }
        System.out.println(stringBuilder);
    }

    @Override
    public Path askPathTo(PrintedDirectoriesContainer printedDirectoriesContainer) {
        System.out.printf("Скинь путь до %s\n", printedDirectoriesContainer.getUserInfo());
        try {
            Path path = Path.of(wrapperInput());
            if(Files.notExists(path)) {
                System.out.println("Такой папки не было, а я возьми, да создай =)");
                Files.createDirectories(path);
            }
            if (!Files.isDirectory(path)) {
                throw new NotDirectoryException(path.toString());
            }
            return path;
        } catch (Exception e) {
            error(e);
            if (wrongAnswerGetAnother()) {
                return askPathTo(printedDirectoriesContainer);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void printContentRange(ContentRange contentRange) {
        System.out.printf("Итак, нам доступен диапазон глав - %s\n", contentRange);
    }

    @Override
    public ContentRange askDesiredContentRange() {
        System.out.println("Укажи диапазон глав включительно, который будем качать");
        String input = wrapperInput();
        List<Integer> foundDigits = RegexUtils.findDigits(input);
        if (isValidRange(foundDigits)) {
            return new ContentRange(foundDigits.getFirst(), foundDigits.getLast());
        }
        if (wrongAnswerGetAnother()) {
            return askDesiredContentRange();
        }
        throw new RuntimeException("Cannot parse range");
    }

    @Override
    public void printErrors(List<ErrorContent> errors) {
        errors.forEach(System.out::println);
    }

    private static boolean isValidRange(List<Integer> foundDigits) {
        return foundDigits.size() == 2 && foundDigits.getFirst() < foundDigits.getLast();
    }

}
