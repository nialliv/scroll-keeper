package ru.artemev.services.impl;

import org.junit.jupiter.api.Test;
import ru.artemev.dto.ErrorContent;
import ru.artemev.services.PrinterService;
import ru.artemev.services.downloaders.Downloader;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PrinterServiceImplTest {

    private final PrinterService printerService = new PrinterServiceImpl();

    void printAvailableDownloaderInfo() {
        String expected = """
                Доступные загрузчики:
                    1 - Качаемся
                    2 - Качаемся
                    3 - Качаемся
                    4 - Качаемся
                    5 - Качаемся
                """;
        List<Downloader> downloaders = Stream.generate(() -> mock(Downloader.class))
                .limit(5)
                .peek(downloader -> when(downloader.getInfo()).thenReturn("Качаемся"))
                .toList();
        printerService.printAvailableDownloaderInfo(downloaders);
    }

    @Test
    void printErrors() {
        List<ErrorContent> errors = buildErrorContents();
        printerService.printErrors(errors);
    }

    private List<ErrorContent> buildErrorContents() {
        return List.of(
                new ErrorContent(1, "https://url.tu/1", new Exception("KEK")),
                new ErrorContent(2, "https://url.tu/2", new Exception("KEK")),
                new ErrorContent(3, "https://url.tu/3", new Exception("KEK")),
                new ErrorContent(4, "https://url.tu/4", new Exception("KEK")),
                new ErrorContent(5, "https://url.tu/5", new Exception("KEK"))
        );
    }
}