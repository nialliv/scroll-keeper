package ru.artemev.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrintedDirectoriesContainer {
    TELEGRAM_EXPORT_CHAT("экспортированный чат"),
    FOLDER_FOR_SAVED_CONTENT("папка куда будем сохранять скаченное");

    private final String userInfo;
}
