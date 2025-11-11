package ru.artemev.services.downloaders;

import ru.artemev.dto.ContentLink;
import ru.artemev.dto.DownloadedContent;
import ru.artemev.dto.ErrorContent;

import java.util.List;

public interface Downloader {

    DownloadedContent download(ContentLink contentLink, List<ErrorContent> errors);

}
