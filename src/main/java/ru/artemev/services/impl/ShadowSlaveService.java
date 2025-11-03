package ru.artemev.services.impl;

import ru.artemev.services.Service;
import ru.artemev.utils.ConsolePrinter;

public class ShadowSlaveService implements Service {

    private final ConsolePrinter consolePrinter = new ConsolePrinter();

    @Override
    public void handle() {
        consolePrinter.getInfoByService(this);
        consolePrinter.printAvailableDownloaders(this);
        // handle downloader
        // handle action
        // do work
    }
}
