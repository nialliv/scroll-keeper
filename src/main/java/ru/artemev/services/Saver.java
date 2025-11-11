package ru.artemev.services;

import ru.artemev.dto.ErrorContent;
import ru.artemev.dto.RanobeTitle;

import java.nio.file.Path;
import java.util.List;

public interface Saver {

    void saveRanobeToPath(RanobeTitle ranobeChapter, Path pathToSaveContent, List<ErrorContent> errors);

}
