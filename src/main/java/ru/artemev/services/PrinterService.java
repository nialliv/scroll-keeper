package ru.artemev.services;

import ru.artemev.dto.ContentRange;
import ru.artemev.dto.ErrorContent;
import ru.artemev.dto.PrintedDirectoriesContainer;
import ru.artemev.services.downloaders.Downloader;

import java.nio.file.Path;
import java.util.List;

public interface PrinterService {

    void printBannerAndGreetings();

    String wrapperInput();

    boolean wrongAnswerGetAnother();

    void error(Exception e);

    void println(String message);

    void printAvailableDownloaderInfo(List<Downloader> downloaders);

    Path askPathTo(PrintedDirectoriesContainer printedDirectoriesContainer);

    void printContentRange(ContentRange contentRange);

    ContentRange askDesiredContentRange();

    void printErrors(List<ErrorContent> errors);
}