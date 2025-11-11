package ru.artemev.services.titles.impl;

import ru.artemev.services.PrinterService;
import ru.artemev.services.impl.PrinterServiceImpl;
import ru.artemev.services.titles.TitleService;

public class LotmTitleService implements TitleService {

    private final PrinterService printerService = new PrinterServiceImpl();

    @Override
    public void handle() {
        // todo implements this
        printerService.println("It's not implemented... =(");
    }

}
