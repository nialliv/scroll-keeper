package ru.artemev.services.impl;

import ru.artemev.dto.Content;
import ru.artemev.dto.ContentRange;
import ru.artemev.dto.ErrorContent;
import ru.artemev.dto.PrintedDirectoriesContainer;
import ru.artemev.services.PrinterService;
import ru.artemev.services.TitleService;
import ru.artemev.services.downloaders.Downloader;
import ru.artemev.services.downloaders.impl.TelegraphDownloader;
import ru.artemev.utils.FileHelper;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShadowSlaveTitleService implements TitleService {

    private static final List<Downloader> DOWNLOADERS = List.of(new TelegraphDownloader());

    private final PrinterService printer = new PrinterServiceImpl();

    @Override
    public void handle() {
        printer.println("Получается качаем теневого раба, возможностей не так много =)");
        printer.printAvailableDownloaderInfo(DOWNLOADERS);

        Downloader downloader = getDownloader();
        //todo
        // сколько глав
        // получить доступный диапазон ОТСОРТИРОВАННЫЙ!

        Path pathToExportChat = printer.askPathTo(PrintedDirectoriesContainer.TELEGRAM_EXPORT_CHAT);
        List<Content> contents = downloader.getAvailableContent(pathToExportChat);
        // запринтить доступный диапазон контента
        ContentRange contentRange = getTheValueBy(contents);
        printer.printContentRange(contentRange);
        // спросить какой диапазон скачать
        ContentRange desiredRange = printer.askDesiredContentRange();
        // спросить куда сохранять
        Path pathToSaveContent = printer.askPathTo(PrintedDirectoriesContainer.FOLDER_FOR_SAVED_CONTENT);
        // в цикле
        List<ErrorContent> errors = new ArrayList<>();
        contents.stream()
                .skip(desiredRange.min())
                .limit(desiredRange.max())
                .parallel()
                //   загружать
//                .map(content -> downloader.download(content, errors))
                //   парсить
//                .map(content -> parser.parseRanobe(content, errors))
                //   сохранять
                .forEach(ranobeChapter -> {
//                    saver.saveRanobeByPath(ranobeChapter, pathToSaveContent, errors);
                    printer.println(String.format("End for process chapter %s", ranobeChapter.getChapterNum()));
                });

        if (!errors.isEmpty()) {
            printer.printErrors(errors);
//            if (printer.askSaveErrorInFile()) {
                FileHelper.saveErrors(pathToSaveContent, errors);
//            }
        }
//        printer.sayFinish();
    }

    private ContentRange getTheValueBy(List<Content> contents) {
        List<Integer> chapterNums = contents.stream()
                .map(Content::getChapterNum)
                .toList();
        Integer min = Collections.min(chapterNums);
        Integer max = Collections.max(chapterNums);
        return new ContentRange(min, max);
    }

    private Downloader getDownloader() {
        // todo goto util class wrapper
        try {
            String input = printer.wrapperInput();
            int downloaderIndex = Integer.parseInt(input);
            return DOWNLOADERS.get(downloaderIndex);
        } catch (Exception e) {
            // todo create recurse if user wanted
            throw new RuntimeException(e);
        }
    }
}
