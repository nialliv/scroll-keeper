package ru.artemev.services.parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import ru.artemev.dto.DownloadedContent;
import ru.artemev.dto.ErrorContent;
import ru.artemev.dto.RanobeTitle;
import ru.artemev.services.PrinterService;
import ru.artemev.services.impl.PrinterServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class RanobeLibParser implements Parser {

    private static final String DATA_FIELD = "data";
    private static final String NUMBER_FIELD = "number";
    private static final String NAME_FIELD = "name";
    private static final String CONTENT_FIELD = "content";
    private static final String TYPE_FIELD = "type";

    private static final String DOC_TYPE = "doc";
    private static final String PARAGRAPH_TYPE = "paragraph";
    private static final String TEXT_TYPE = "text";

    private static final String TITLE_TEMPLATE = "Глава %s: %s";
    private static final String P_TAG = "p";

    private final PrinterService printerService = new PrinterServiceImpl();

    @Override
    public RanobeTitle parse(DownloadedContent downloadedContent, List<ErrorContent> errors) {
        try {
            JsonObject root = JsonParser.parseString(downloadedContent.body()).getAsJsonObject();
            JsonObject data = root.get(DATA_FIELD).getAsJsonObject();

            int chapterNumber = data.get(NUMBER_FIELD).getAsInt();
            String chapterName = data.get(NAME_FIELD).getAsString();

            List<String> paragraphs = new ArrayList<>();

            JsonElement content = data.get(CONTENT_FIELD);
            if (content.isJsonObject()) {
                findParagraphsByJsonObject(content, paragraphs);
            } else {
                paragraphs = getParagraphsByHtmlString(content);
            }
            return new RanobeTitle(String.format(TITLE_TEMPLATE, chapterNumber, chapterName), paragraphs);
        } catch (Exception ex) {
            printerService.error(ex);
            errors.add(new ErrorContent(downloadedContent.chapterNum(), ex));
            return null;
        }
    }

    private static List<String> getParagraphsByHtmlString(JsonElement content) {
        List<String> paragraphs;
        paragraphs = Jsoup.parse(content.getAsString())
                .getElementsByTag(P_TAG)
                .stream()
                .map(Element::text)
                .toList();
        return paragraphs;
    }

    private static void findParagraphsByJsonObject(JsonElement content, List<String> paragraphs) {
        JsonObject dataContent = content.getAsJsonObject();
        String dataContentType = dataContent.get(TYPE_FIELD).getAsString();
        if (DOC_TYPE.equals(dataContentType)) {
            enrichParagraphs(dataContentType, dataContent, paragraphs);
        }
    }

    private static void enrichParagraphs(String dataContentType, JsonObject dataContent, List<String> paragraphs) {
        JsonArray contentArray = dataContent.get(CONTENT_FIELD).getAsJsonArray();
        contentArray.forEach(content -> {
            JsonObject contentObject = content.getAsJsonObject();

            if (contentObject.get(TYPE_FIELD).getAsString().equals(PARAGRAPH_TYPE)) {
                if (contentObject.isEmpty()) {
                    return;
                }
                JsonArray contentWithText = contentObject.get(CONTENT_FIELD).getAsJsonArray();
                StringBuilder paragraphBuilder = new StringBuilder();
                for (JsonElement paragraphNode : contentWithText) {
                    JsonElement jsonElement = paragraphNode.getAsJsonObject().get(TEXT_TYPE);
                    if (jsonElement != null) {
                        paragraphBuilder.append(jsonElement.getAsString());
                    }
                }
                paragraphs.add(paragraphBuilder.toString());
            }
        });
    }
}
