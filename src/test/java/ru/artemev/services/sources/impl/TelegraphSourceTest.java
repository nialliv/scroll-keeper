package ru.artemev.services.sources.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

class TelegraphSourceTest {

    private final TelegraphSource telegraphSource = new TelegraphSource();

    @Test
    void getAvailableContent() {
        try (JsonReader reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(TelegraphSourceTest.class.getClassLoader().getResourceAsStream("checkRanobe.json"))))) {
            JsonElement jsonElement = JsonParser.parseReader(reader).getAsJsonObject().get("data");
            jsonElement.getAsJsonArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}