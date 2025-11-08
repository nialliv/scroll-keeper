package ru.artemev.services.downloaders.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.artemev.dto.Content;
import ru.artemev.services.downloaders.Downloader;
import ru.artemev.utils.FileHelper;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TelegraphDownloader implements Downloader {

    @Override
    public String getInfo() {
        return "Можно скачать из телеги, но для этого нужен экспортированный чат";
    }

    @Override
    public List<Content> getAvailableContent(Path pathToExportChat) {

        // todo change to
        JSONObject json = FileHelper.getJsonFromFile(pathToExportChat);
        // for each
        JSONArray messages = json.getJSONArray("messages");
        List<Content> contents = new ArrayList<>(messages.length());
        messages.forEach(element -> processByElement((JSONObject) element, contents));
        // get title and url to download
        // return ?

        return contents;
    }

    private void processByElement(JSONObject element, List<Content> contents) {
        JSONArray textField = element.getJSONArray("text_entities");
    }
}
