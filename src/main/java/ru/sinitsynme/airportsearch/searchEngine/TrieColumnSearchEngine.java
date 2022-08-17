package ru.sinitsynme.airportsearch.searchEngine;

import ru.sinitsynme.airportsearch.exception.SearchException;
import ru.sinitsynme.airportsearch.parser.TableParser;
import ru.sinitsynme.airportsearch.prefixTree.PrefixTree;
import ru.sinitsynme.airportsearch.searchResult.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class TrieColumnSearchEngine implements ColumnSearchEngine {

    private PrefixTree prefixTree;
    private final TableParser tableParser;

    protected Map<Integer, String> searchColumnData;

    public TrieColumnSearchEngine(TableParser tableParser, int column) {

        this.tableParser = tableParser;
        prepareEngineForSearchInColumn(column);
    }

    @Override
    public void prepareEngineForSearchInColumn(int column) {
        searchColumnData = tableParser.getColumnDataAndId(column);

        prefixTree = new PrefixTree();
        prefixTree.addMultipleData(searchColumnData);
    }

    @Override
    public List<String> search(String query) {
        query = query.trim();

        if (query.trim().length() == 0) {
            throw new SearchException("Запрос должен содержать в себе непробельные символы!");
        }

        List<Integer> matchedIds = prefixTree.searchIdsByPrefix(query);

        List<SearchResult> resultList = filterAndGetSearchData(matchedIds);

        return formatResult(resultList, matchedIds);
    }

    protected abstract List<SearchResult> filterAndGetSearchData(List<Integer> ids);

    private List<String> formatResult(List<SearchResult> resultList, List<Integer> matchedIds) {
        List<String> formattedResultList = new ArrayList<>();

        Map<Integer, String> rows = tableParser.getDataByRowIds(matchedIds);

        for (SearchResult searchResult: resultList) {

            int resultId = searchResult.getId();

            String singleResult = String.format("%s[%s]", searchResult.getStringData(), rows.get(resultId));
            formattedResultList.add(singleResult);

        }

        return formattedResultList;
    }
}
