package ru.artemev.services.ustils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class ObjectBuilder {

    private static final ClassLoader CLASS_LOADER = ObjectBuilder.class.getClassLoader();
    private static final Gson GSON = new Gson();

    public static Path getPathFromTestResources(String filePath) {
        URL resource = CLASS_LOADER.getResource(filePath);
        return Path.of(Objects.requireNonNull(resource).getPath());
    }

    public static <T> T buildJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    @SneakyThrows
    public static <T> List<T> buildJsonList(String filePath) {
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(CLASS_LOADER.getResourceAsStream(filePath)))) {
            return GSON.fromJson(reader, new TypeToken<>() {
            }.getType());
        }
    }
}
