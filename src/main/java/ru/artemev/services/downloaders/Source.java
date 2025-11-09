package ru.artemev.services.downloaders;

import ru.artemev.dto.Content;

import java.nio.file.Path;
import java.util.List;

public interface Source {

    String getInfo();

    List<Content> getAvailableContent(Path pathToExportChat);

}
