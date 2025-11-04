package ru.artemev.services.impl;

import ru.artemev.services.PrinterService;
import ru.artemev.services.TitleService;
import ru.artemev.services.downloaders.Downloader;
import ru.artemev.services.downloaders.impl.TelegraphDownloader;

import java.util.List;

public class ShadowSlaveTitleService implements TitleService {

    private static final List<Downloader> DOWNLOADERS = List.of(new TelegraphDownloader());

    private final PrinterService printer = new PrinterServiceImpl();

    @Override
    public void handle() {
        printer.println("Получается качаем теневого раба, возможностей не так много =)");
        printer.printAvailableDownloaderInfo(DOWNLOADERS);

        Downloader downloader = getDownloader();

//        downloader.dow

        // handle downloader
        // handle action
        // do work
    }

    private Downloader getDownloader() {
        // todo goto util class wrapper
        try {
            String input = printer.wrapperInput();
            int downloaderIndex = Integer.parseInt(input);
            return DOWNLOADERS.get(downloaderIndex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
