package ru.artemev.dto;

public record ErrorContent(
        int chapterNum,
        Exception exception
) {
}
