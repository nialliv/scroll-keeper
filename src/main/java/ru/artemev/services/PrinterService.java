package ru.artemev.services;

import ru.artemev.dto.ChapterRange;
import ru.artemev.dto.ErrorContent;
import ru.artemev.dto.PrintedDirectoriesContainer;
import ru.artemev.services.sources.Source;

import java.nio.file.Path;
import java.util.List;

public interface PrinterService {

    void printBannerAndGreetings();

    String wrapperInput();

    boolean wrongAnswerGetAnother();

    void error(Exception e);

    void println(String message);

    void printAvailableSourceInfo(List<Source> sources);

    Path askPathTo(PrintedDirectoriesContainer printedDirectoriesContainer);

    void printContentRange(ChapterRange chapterRange);

    ChapterRange askDesiredContentRange();

    void printErrors(List<ErrorContent> errors);

    void sayFinish();
}