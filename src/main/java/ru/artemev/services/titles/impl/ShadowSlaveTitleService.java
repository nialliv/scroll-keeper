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
import ru.artemev.services.sources.impl.RanobeApi;
import ru.artemev.services.sources.impl.TelegraphSource;
import ru.artemev.services.titles.TitleService;
import ru.artemev.utils.FileHelper;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShadowSlaveTitleService implements TitleService {

    private static final List<Source> SOURCE_LIST = List.of(new TelegraphSource(), new RanobeApi());

    private final PrinterService printerService = new PrinterServiceImpl();
    private final Downloader downloader = new HttpDownloader();
    private final Saver saver = new DocSaver();

    @Override
    public void handle() {
        printerService.println("Получается качаем теневого раба, возможностей не так много =)");
        printerService.printAvailableSourceInfo(SOURCE_LIST);

        final Source source = getSource();

        List<ContentLink> contentLinks = getContentLinks(source);

        Path pathToSaveContent = printerService.askPathTo(PrintedDirectoriesContainer.FOLDER_FOR_SAVED_CONTENT);

        List<ErrorContent> errors = new ArrayList<>();
        contentLinks.stream()
                .parallel()
                .map(content -> downloader.download(content, errors))
                .map(content -> ParserResolver.getParserBySource(source).parse(content, errors))
                .forEach(ranobeTitle -> {
                    saver.saveRanobeToPath(ranobeTitle, pathToSaveContent, errors);
                    printerService.println(String.format("End for process title - %s", ranobeTitle.title()));
                });

        if (!errors.isEmpty()) {
            printerService.printErrors(errors);
            FileHelper.saveErrors(pathToSaveContent, errors);
        }
        printerService.sayFinish();
    }

    private List<ContentLink> getContentLinks(Source source) {
        List<ContentLink> contentLinks = source.getAvailableContent();

        ChapterRange chapterRange = getTheValueBy(contentLinks);
        printerService.printContentRange(chapterRange);

        ChapterRange desiredRange = printerService.askDesiredContentRange();
        return contentLinks.stream()
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

    private Source getSource() {
        // todo goto util class wrapper or ResolverClass
        try {
            String input = printerService.wrapperInput();
            int sourceIndex = Integer.parseInt(input);
            return SOURCE_LIST.get(sourceIndex - 1);
        } catch (Exception e) {
            // todo create recurse if user wanted
            throw new RuntimeException(e);
        }
    }
}
