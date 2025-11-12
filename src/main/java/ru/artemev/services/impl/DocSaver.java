package ru.artemev.services.impl;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import ru.artemev.dto.ErrorContent;
import ru.artemev.dto.RanobeTitle;
import ru.artemev.services.PrinterService;
import ru.artemev.services.Saver;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.List;

public class DocSaver implements Saver {

    private final PrinterService printerService = new PrinterServiceImpl();

    @Override
    public void saveRanobeToPath(RanobeTitle ranobeChapter, Path pathToSaveContent, List<ErrorContent> errors) {
        String title = ranobeChapter.title();
        try (XWPFDocument document = new XWPFDocument()) {
            createParagraph(document, title, 18, true, ParagraphAlignment.CENTER);
            ranobeChapter.lines()
                    .forEach(line -> createParagraph(document, line, 12, false, ParagraphAlignment.LEFT));

            String fileName = title.substring(0, Math.min(title.length(), 80)) + ".docx";
            File documentFile = pathToSaveContent.resolve(fileName).toFile();
            document.write(new FileOutputStream(documentFile));
        } catch (Exception ex) {
            printerService.error(ex);
            errors.add(new ErrorContent(Integer.parseInt(title), ex));
        }
    }

    private static void createParagraph(XWPFDocument document, String text, int fontSize, boolean isBold, ParagraphAlignment alignment) {
        XWPFParagraph titleParagraph = document.createParagraph();
        titleParagraph.setAlignment(alignment);
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText(text);
        titleRun.setFontSize(fontSize);
        titleRun.setBold(isBold);
        titleRun.addCarriageReturn();
    }
}
