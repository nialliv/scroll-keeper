package ru.artemev.services.downloaders;

import ru.artemev.dto.Content;

import java.nio.file.Path;
import java.util.List;

public interface Downloader {

    String getInfo();

    List<Content> getAvailableContent(Path pathToExportChat);
}
