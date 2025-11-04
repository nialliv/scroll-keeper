package ru.artemev.services.impl;

import org.junit.jupiter.api.Test;
import ru.artemev.services.PrinterService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrinterServiceImplTest {

    private final PrinterService printerService = new PrinterServiceImpl();

    @Test
    void printAvailableDownloaderInfo() {
        printerService.printAvailableDownloaderInfo(List.of());
    }
}