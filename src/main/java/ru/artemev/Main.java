package ru.artemev;

import ru.artemev.dto.ServiceContainer;
import ru.artemev.utils.ConsolePrinter;

public class Main {

    public static void main(String[] args) {
        ConsolePrinter printer = new ConsolePrinter();
        printer.printBannerAndGreetings();

        for (ServiceContainer container : ServiceContainer.values()) {
            System.out.printf("%d - %s\n", container.ordinal(), container.getDescriptionService());
        }

        String serviceNum = printer.wrapperInput();
        ServiceContainer.values()
                [Integer.parseInt(serviceNum)]
                .getService().get().handle();

    }

}
