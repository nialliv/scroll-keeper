package ru.artemev.services.downloaders.impl;

import ru.artemev.dto.Content;
import ru.artemev.services.downloaders.Downloader;

import java.nio.file.Path;
import java.util.List;

public class HttpDownloader implements Downloader {
    @Override
    public String getInfo() {
        return "";
    }

    @Override
    public List<Content> getAvailableContent(Path pathToExportChat) {
        return List.of();
    }
}
