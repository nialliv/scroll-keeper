package ru.artemev.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FileHelper {

    public static String getContentFromResource(String path) {
        try (var inputStream = FileHelper.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IOException("Файл не найден в resources: " + path);
            }
            byte[] bytes = inputStream.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            return null;
        }
    }
}
