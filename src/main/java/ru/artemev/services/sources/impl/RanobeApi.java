package ru.artemev.services.sources.impl;

import com.google.gson.Gson;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import ru.artemev.config.HttpConfig;
import ru.artemev.dto.ContentLink;
import ru.artemev.dto.json.RanobeBranchTranslators;
import ru.artemev.dto.json.RanobeLibData;
import ru.artemev.dto.json.RanobeLibResponse;
import ru.artemev.services.PrinterService;
import ru.artemev.services.impl.PrinterServiceImpl;
import ru.artemev.services.sources.Source;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class RanobeApi implements Source {

    // todo fix this
    private static final String SOURCE = "https://api.cdnlibs.org/api/manga/122448--shadow-slave/chapters";
    private static final String URL_TO_DOWNLOAD_FORMAT = "https://api.cdnlibs.org/api/manga/122448--shadow-slave/chapter?branch_id=13303&number=%s&volume=%s";
    private final PrinterService printerService = new PrinterServiceImpl();

    @Override
    public String getInfo() {
        return "Скачать из ranobe lib";
    }

    @Override
    public List<ContentLink> getAvailableContent() {
        RanobeLibResponse response = getResponse();

        return response.getData()
                .stream()
                .filter(RanobeApi::isContainsBranchId)
                .map(data -> new ContentLink(data.getNumber(), String.format(URL_TO_DOWNLOAD_FORMAT, data.getNumber(), data.getVolume())))
                .toList();
    }

    private static boolean isContainsBranchId(RanobeLibData data) {
        return data.getBranches()
                .stream()
                .map(RanobeBranchTranslators::getBranchId)
                .anyMatch(branchId -> branchId != null && branchId.equals(13303));
    }

    private RanobeLibResponse getResponse() {
        try {
            return HttpConfig.getHttpClient()
                    .execute(new HttpGet(SOURCE),
                            httpResponse -> {
                                InputStream content = httpResponse.getEntity().getContent();
                                return new Gson().fromJson(new InputStreamReader(content), RanobeLibResponse.class);
                            });
        } catch (Exception ex) {
            printerService.error(ex);
            throw new RuntimeException(ex);
        }
    }
}
