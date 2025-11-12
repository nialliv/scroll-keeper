package ru.artemev.services.sources;

import ru.artemev.dto.ContentLink;

import java.util.List;

public interface Source {

    String getInfo();

    List<ContentLink> getAvailableContent();

}
