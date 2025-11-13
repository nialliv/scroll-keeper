package ru.artemev.services.downloaders.impl;

import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import ru.artemev.config.HttpConfig;
import ru.artemev.dto.ContentLink;
import ru.artemev.dto.DownloadedContent;
import ru.artemev.dto.ErrorContent;
import ru.artemev.services.PrinterService;
import ru.artemev.services.downloaders.Downloader;
import ru.artemev.services.impl.PrinterServiceImpl;

import java.io.IOException;
import java.util.List;

public class HttpDownloader implements Downloader {

    private final PrinterService printerService = new PrinterServiceImpl();

    @SneakyThrows
    @Override
    public DownloadedContent download(ContentLink contentLink, List<ErrorContent> errors) {
        try {
            String htmlBody = HttpConfig.getHttpClient()
                    .execute(new HttpGet(contentLink.url()),
                            httpResponse -> {
                                if (httpResponse.getCode() == 200) {
                                    return EntityUtils.toString(httpResponse.getEntity());
                                }
                                throw new HttpException();
                            });
            return new DownloadedContent(contentLink.chapterNum(), htmlBody);
        } catch (IOException ex) {
            errors.add(new ErrorContent(contentLink.chapterNum(), ex));
            printerService.println("Something went wrong downloading " + contentLink.url());
            return null;
        }
    }

}
