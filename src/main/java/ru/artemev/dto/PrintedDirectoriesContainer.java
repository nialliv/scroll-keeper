package ru.artemev.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum PrintedDirectoriesContainer {
    TELEGRAM_EXPORT_CHAT("экспортированного чата"),
    FOLDER_FOR_SAVED_CONTENT("папки, куда будем сохранять скаченное");

    private final String userInfo;

    private final static List<PrintedDirectoriesContainer> FOLDERS_LIST = List.of(FOLDER_FOR_SAVED_CONTENT);

    public static List<PrintedDirectoriesContainer> getFolders() {
        return FOLDERS_LIST;
    }
}
