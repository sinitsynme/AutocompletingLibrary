package ru.sinitsynme.airportsearch.searchEngine.impl;

import ru.sinitsynme.airportsearch.parser.TableParser;
import ru.sinitsynme.airportsearch.searchEngine.TrieColumnSearchEngine;
import ru.sinitsynme.airportsearch.searchResult.SearchResult;
import ru.sinitsynme.airportsearch.searchResult.impl.StringSearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringColumnSearchEngine extends TrieColumnSearchEngine {

    public StringColumnSearchEngine(TableParser tableParser, int column) {
        super(tableParser, column);
    }

    @Override
    protected List<SearchResult> filterSearchData(List<Integer> ids) {
        List<StringSearchResult> stringSearchResults = new ArrayList<>();

        for (Integer id : ids) {
            stringSearchResults.add(new StringSearchResult(id, searchData.get(id)));
        }

        Collections.sort(stringSearchResults);

        return (List<SearchResult>) (List<?>) stringSearchResults;


    }
}
