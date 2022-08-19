package ru.sinitsynme.airportsearch.searchEngine.impl;

import ru.sinitsynme.airportsearch.parser.TableParser;
import ru.sinitsynme.airportsearch.searchEngine.TrieColumnSearchEngine;
import ru.sinitsynme.airportsearch.searchResult.SearchResult;
import ru.sinitsynme.airportsearch.searchResult.impl.NumberSearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumberColumnSearchEngine extends TrieColumnSearchEngine {

    public NumberColumnSearchEngine(TableParser tableParser, int column) {
        super(tableParser, column);
    }

    @Override
    protected List<SearchResult> filterAndGetSearchData(List<Integer> ids) {
        List<NumberSearchResult> numberSearchResults = new ArrayList<>();

        for (Integer id : ids) {
            numberSearchResults.add(new NumberSearchResult(id, Double.parseDouble(searchColumnData.get(id))));
        }

        Collections.sort(numberSearchResults);

        return (List<SearchResult>) (List<?>) numberSearchResults;
    }
}
