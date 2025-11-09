package ru.artemev.services.downloaders.impl;

import ru.artemev.dto.Content;
import ru.artemev.dto.ErrorContent;

import java.util.List;

public interface Downloader {

    String download(Content content, List<ErrorContent> errors);

}
