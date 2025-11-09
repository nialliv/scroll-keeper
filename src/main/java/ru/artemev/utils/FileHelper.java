package ru.artemev.utils;

import com.google.gson.Gson;
import ru.artemev.dto.ErrorContent;
import ru.artemev.dto.json.ExportChat;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class FileHelper {

    private static final Path FILENAME_ERROR_LIST = Path.of("errors.txt");
    private static final Gson GSON_INSTANCE = new Gson();

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

    public static void saveErrors(Path pathToSaveContent, List<ErrorContent> errors) {
        Path filePath = pathToSaveContent.resolve(FILENAME_ERROR_LIST);
        String lines = errors.stream()
                .map(FileHelper::getLineByContent)
                .collect(Collectors.joining("\n"));
        try {
            Files.writeString(filePath, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getLineByContent(ErrorContent content) {
        return String.format("Chapter - %s\nURL - %s\nException - %s\n", content.chapterNum(), content.url(), content.exception());
    }

    public static ExportChat getJsonFromFile(Path pathToExportChat) {
        try(Reader reader = Files.newBufferedReader(pathToExportChat)) {
            return GSON_INSTANCE.fromJson(reader, ExportChat.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
