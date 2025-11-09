package ru.artemev.utils;

import java.util.List;
import java.util.regex.Pattern;

public class RegexUtils {

    private static final String DIGIT_REGEX = "\\d+";

    public static List<Integer> findDigits(String input) {
        return Pattern.compile(DIGIT_REGEX)
                .matcher(input)
                .results()
                .map(match -> Integer.parseInt(match.group()))
                .toList();
    }

}
