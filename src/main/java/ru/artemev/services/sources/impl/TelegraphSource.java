package ru.artemev.services.sources.impl;

import org.apache.commons.lang3.Strings;
import ru.artemev.dto.ChapterRange;
import ru.artemev.dto.ContentLink;
import ru.artemev.dto.PrintedDirectoriesContainer;
import ru.artemev.dto.json.ExportChat;
import ru.artemev.dto.json.Message;
import ru.artemev.dto.json.TextEntity;
import ru.artemev.services.PrinterService;
import ru.artemev.services.impl.PrinterServiceImpl;
import ru.artemev.services.sources.Source;
import ru.artemev.utils.FileHelper;
import ru.artemev.utils.RegexUtils;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TelegraphSource implements Source {

    private static final java.lang.String TEXT_LINK_FIELD = "text_link";

    private final PrinterService printerService = new PrinterServiceImpl();

    @Override
    public String getInfo() {
        return "Можно скачать из телеги, но для этого нужен экспортированный чат";
    }

    @Override
    public List<ContentLink> getAvailableContent() {
        Path pathToExportChat = printerService.askPathTo(PrintedDirectoriesContainer.TELEGRAM_EXPORT_CHAT);
        ExportChat json = FileHelper.getJsonFromFile(pathToExportChat);
        if (json == null) {
            throw new RuntimeException("json not valid");
        }

        List<Message> messages = json.getMessages();
        List<ContentLink> result = messages.stream()
                .flatMap(message -> message.getTextEntities().stream())
                .filter(TelegraphSource::filterTextEntity)
                .map(TelegraphSource::mapToContent)
                .sorted(Comparator.comparingInt(ContentLink::chapterNum))
                .toList();

        ChapterRange chapterRange = getTheValueBy(result);
        printerService.printContentRange(chapterRange);
        ChapterRange desiredRange = printerService.askDesiredContentRange();
        return result.stream()
                .filter(contentLink -> desiredRange.min() <= contentLink.chapterNum() && contentLink.chapterNum() <= desiredRange.max())
                .toList();
    }

    private ChapterRange getTheValueBy(List<ContentLink> contentLinks) {
        List<Integer> chapterNums = contentLinks.stream()
                .map(ContentLink::chapterNum)
                .toList();
        Integer min = Collections.min(chapterNums);
        Integer max = Collections.max(chapterNums);
        return new ChapterRange(min, max);
    }

    private static boolean filterTextEntity(TextEntity textEntity) {
        return textEntity != null && TEXT_LINK_FIELD.equals(textEntity.getType()) && Strings.CI.contains(textEntity.getText(), "глава");
    }

    private static ContentLink mapToContent(TextEntity textEntity) {
        List<Integer> chapterNum = RegexUtils.findDigits(textEntity.getText());
        if (chapterNum.size() != 1) {
            throw new RuntimeException("Error find chapter number");
        }
        return new ContentLink(chapterNum.getFirst(), textEntity.getHref());
    }
}