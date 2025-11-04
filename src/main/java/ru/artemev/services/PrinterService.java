package ru.artemev.services;

import ru.artemev.services.downloaders.Downloader;

import java.util.List;

public interface PrinterService {

    void printBannerAndGreetings();

    String wrapperInput();

    boolean wrongAnswerGetAnother();

    void error(Exception e);

    void println(String message);

    void printAvailableDownloaderInfo(List<Downloader> downloaders);
}