package ru.artemev.services.parsers.impl;

import org.apache.commons.lang3.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ru.artemev.dto.DownloadedContent;
import ru.artemev.dto.ErrorContent;
import ru.artemev.dto.RanobeTitle;
import ru.artemev.services.PrinterService;
import ru.artemev.services.impl.PrinterServiceImpl;
import ru.artemev.services.parsers.Parser;

import java.util.List;

//todo mb should rename to telegraph, not html
public class TelegraphParser implements Parser {

    public static final String ARTICLE_TAG = "article";
    public static final String H1_TAG = "h1";
    public static final String P_TAG = "p";

    private final PrinterService printerService = new PrinterServiceImpl();

    @Override
    public RanobeTitle parse(DownloadedContent content, List<ErrorContent> errors) {
        try {
            Document doc = Jsoup.parse(content.body());
            Element article = doc.getElementsByTag(ARTICLE_TAG).getFirst();
            String title = article.getElementsByTag(H1_TAG).getFirst().text();
            List<String> paragraphs = article.getElementsByTag(P_TAG)
                    .stream()
                    .map(Element::text)
                    .filter(text -> !Strings.CI.equalsAny(text, "Предыдущая глава", "", "Следующая глава"))
                    .toList();
            return new RanobeTitle(title, paragraphs);
        } catch (Exception ex) {
            errors.add(new ErrorContent(content.chapterNum(), ex));
            printerService.error(ex);
            return null;
        }
    }
}
