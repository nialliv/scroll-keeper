package ru.artemev.services.parsers;

import ru.artemev.dto.DownloadedContent;
import ru.artemev.dto.ErrorContent;
import ru.artemev.dto.RanobeTitle;

import java.util.List;

public interface Parser {

    RanobeTitle parse(DownloadedContent html, List<ErrorContent> errors);

}
