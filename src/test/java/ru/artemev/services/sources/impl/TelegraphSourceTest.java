package ru.artemev.services.sources.impl;

import org.junit.jupiter.api.Test;
import ru.artemev.dto.ContentLink;
import ru.artemev.services.ustils.ObjectBuilder;

import java.util.List;

class TelegraphSourceTest {

    private final TelegraphSource telegraphSource = new TelegraphSource();

    @Test
    void getAvailableContent() {
        List<ContentLink> expected = ObjectBuilder.buildJsonList("expectedListContent.json");
        List<ContentLink> actual = telegraphSource.getAvailableContent(ObjectBuilder.getPathFromTestResources("exportChatTest.json"));
//        assertThat(actual)
//                .usingRecursiveFieldByFieldElementComparator()
//                .isSameAs(expected);
    }
}