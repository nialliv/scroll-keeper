package ru.artemev.dto;

public record ErrorContent(
        int chapterNum,
        String url, // todo mb change to URI class
        Exception exception
) {
}
