package ru.artemev.services.titles.impl;

import ru.artemev.dto.ChapterRange;
import ru.artemev.dto.ContentLink;
import ru.artemev.dto.ErrorContent;
import ru.artemev.dto.PrintedDirectoriesContainer;
import ru.artemev.services.PrinterService;
import ru.artemev.services.Saver;
import ru.artemev.services.downloaders.Downloader;
import ru.artemev.services.downloaders.impl.HttpDownloader;
import ru.artemev.services.impl.DocSaver;
import ru.artemev.services.impl.PrinterServiceImpl;
import ru.artemev.services.resolvers.ParserResolver;
import ru.artemev.services.sources.Source;
import ru.artemev.services.sources.impl.TelegraphSource;
import ru.artemev.services.titles.TitleService;
import ru.artemev.utils.FileHelper;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShadowSlaveTitleService implements TitleService {

    private static final List<Source> SOURCE_LIST = List.of(new TelegraphSource());

    private final PrinterService printer = new PrinterServiceImpl();
    private final Downloader downloader = new HttpDownloader();
    private final Saver saver = new DocSaver();

    @Override
    public void handle() {
        printer.println("Получается качаем теневого раба, возможностей не так много =)");
        printer.printAvailableSourceInfo(SOURCE_LIST);

        final Source source = getSource();

        // todo fix logic, if source - not telegraph this not needed
        // mb create new handler ....
        Path pathToExportChat = printer.askPathTo(PrintedDirectoriesContainer.TELEGRAM_EXPORT_CHAT);
        List<ContentLink> contentLinks = source.getAvailableContent(pathToExportChat);
        // UDPDATE - no, no, no, create simple switch we need download content and that's it

        ChapterRange chapterRange = getTheValueBy(contentLinks);
        printer.printContentRange(chapterRange);
        ChapterRange desiredRange = printer.askDesiredContentRange();

        Path pathToSaveContent = printer.askPathTo(PrintedDirectoriesContainer.FOLDER_FOR_SAVED_CONTENT);

        List<ErrorContent> errors = new ArrayList<>();
        contentLinks.stream()
                .filter(contentLink -> desiredRange.min() <= contentLink.chapterNum() && contentLink.chapterNum() <= desiredRange.max())
                .parallel()
                .map(content -> downloader.download(content, errors))
                .map(content -> ParserResolver.getParserBySource(source).parse(content, errors))
                .forEach(ranobeTitle -> {
                    saver.saveRanobeToPath(ranobeTitle, pathToSaveContent, errors);
                    printer.println(String.format("End for process title - %s", ranobeTitle.title()));
                });

        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            FileHelper.saveErrors(pathToSaveContent, errors);
        }
        printer.sayFinish();
    }

    private ChapterRange getTheValueBy(List<ContentLink> contentLinks) {
        List<Integer> chapterNums = contentLinks.stream()
                .map(ContentLink::chapterNum)
                .toList();
        Integer min = Collections.min(chapterNums);
        Integer max = Collections.max(chapterNums);
        return new ChapterRange(min, max);
    }

    private Source getSource() {
        // todo goto util class wrapper or ResolverClass
        try {
            String input = printer.wrapperInput();
            int sourceIndex = Integer.parseInt(input);
            return SOURCE_LIST.get(sourceIndex - 1);
        } catch (Exception e) {
            // todo create recurse if user wanted
            throw new RuntimeException(e);
        }
    }
}
