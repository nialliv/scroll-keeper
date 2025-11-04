package ru.artemev;

import ru.artemev.dto.ServiceContainer;
import ru.artemev.services.PrinterService;
import ru.artemev.services.TitleService;
import ru.artemev.services.impl.PrinterServiceImpl;

public class Main {

    private static final PrinterService printer =  new PrinterServiceImpl();

    public static void main(String[] args) {
        printer.printBannerAndGreetings();

        for (ServiceContainer container : ServiceContainer.values()) {
            System.out.printf("%d - %s\n", container.ordinal(), container.getDescriptionService());
        }

        getService().handle();

    }

    private static TitleService getService() {
        try {
            int indexService = Integer.parseInt(printer.wrapperInput());

            return ServiceContainer.values()
                    [indexService]
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
