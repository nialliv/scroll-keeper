package ru.artemev.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.artemev.services.Service;
import ru.artemev.services.impl.LotmService;
import ru.artemev.services.impl.ShadowSlaveService;

import java.util.function.Supplier;

@AllArgsConstructor
@Getter
public enum ServiceContainer {

    SHADOW_SLAVE_DOWNLOADER("Shadow slave", ShadowSlaveService::new),
    LORD_OF_THE_MYSTERIES("Lord of the mysteries", LotmService::new),
    ;

    private final String descriptionService;
    private final Supplier<Service> service;
}
