package ru.artemev.utils;

import java.util.List;
import java.util.regex.Pattern;

public class RegexUtils {

    public static List<Integer> findDigits(String input) {
        return Pattern.compile("\\d+")
                .matcher(input)
                .results()
                .map(match -> Integer.parseInt(match.group()))
                .toList();
    }

}
