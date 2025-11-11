package ru.artemev.services.resolvers;

import ru.artemev.services.parsers.Parser;
import ru.artemev.services.parsers.impl.HtmlParser;
import ru.artemev.services.sources.Source;
import ru.artemev.services.sources.impl.TelegraphSource;

import java.util.HashMap;
import java.util.Map;

public class ParserResolver {

    private ParserResolver() {}

    private static final Map<String, Parser> PARSERS = new HashMap<>();

    static {
        PARSERS.put(TelegraphSource.class.getSimpleName(), new HtmlParser());
    }

    public static Parser getParserBySource(Source source) {
        return PARSERS.get(source.getClass().getSimpleName());
    }
}
