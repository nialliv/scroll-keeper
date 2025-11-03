package ru.artemev.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.artemev.services.Service;
import ru.artemev.services.impl.LotmService;
import ru.artemev.services.impl.ShadowSlaveService;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum ServiceDescription {
    SHADOW_SLAVE(ShadowSlaveService.class, "Итак, качаем теневого раба, можно пока только вытаскивать из телеграфа"),
    LOTM(LotmService.class, "Итак, качаем лотм, можно пока только вытаскивать из телеграфа"),;

    private final Class<? extends Service> service;
    private final String greetingsHandler;

    public static String getDescriptionByService(Class<? extends Service> clazz) {
        return Arrays.stream(values())
                .filter(container -> container.service.equals(clazz))
                .findAny()
                .map(ServiceDescription::getGreetingsHandler)
                .orElseThrow(() -> new RuntimeException("Cannot find Service"));
    }
}
