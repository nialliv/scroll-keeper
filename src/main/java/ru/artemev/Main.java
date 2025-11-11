package ru.artemev;

import ru.artemev.dto.ServiceContainer;
import ru.artemev.services.PrinterService;
import ru.artemev.services.titles.TitleService;
import ru.artemev.services.impl.PrinterServiceImpl;

public class Main {

    private static final PrinterService printer =  new PrinterServiceImpl();

    public static void main(String[] args) {
        printer.printBannerAndGreetings();

        // todo go to printer
        for (ServiceContainer container : ServiceContainer.values()) {
            System.out.printf("%d - %s\n", container.ordinal() + 1, container.getDescriptionService());
        }

        getService().handle();

    }

    // todo go to special resolver
    private static TitleService getService() {
        try {
            int indexService = Integer.parseInt(printer.wrapperInput());

            return ServiceContainer.values()
                    [indexService - 1]
                    .getService().get();
        } catch (Exception e) {
            if(printer.wrongAnswerGetAnother()) {
                return getService();
            }
            printer.error(e);
            throw e;
        }
    }

}
