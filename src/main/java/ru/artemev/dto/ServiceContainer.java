package ru.artemev.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.artemev.services.titles.TitleService;
import ru.artemev.services.titles.impl.LotmTitleService;
import ru.artemev.services.titles.impl.ShadowSlaveTitleService;

import java.util.function.Supplier;

@AllArgsConstructor
@Getter
public enum ServiceContainer {

    SHADOW_SLAVE_DOWNLOADER("Shadow slave", ShadowSlaveTitleService::new),
    LORD_OF_THE_MYSTERIES("Lord of the mysteries", LotmTitleService::new),
    ;

    private final String descriptionService;
    private final Supplier<TitleService> service;
}
