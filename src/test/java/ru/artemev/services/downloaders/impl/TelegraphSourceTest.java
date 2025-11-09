package ru.artemev.services.downloaders.impl;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class TelegraphSourceTest {

    private final TelegraphSource telegraphSource = new TelegraphSource();

    @Test
    void getAvailableContent() {
        telegraphSource.getAvailableContent(Path.of("test.json"));
    }
}