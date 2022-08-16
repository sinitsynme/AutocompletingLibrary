package ru.sinitsynme.airportsearch.searchEngine.impl;

import ru.sinitsynme.airportsearch.exception.SearchException;
import ru.sinitsynme.airportsearch.parser.TableParser;
import ru.sinitsynme.airportsearch.prefixTree.PrefixTree;
import ru.sinitsynme.airportsearch.searchEngine.ColumnSearchEngine;
import ru.sinitsynme.airportsearch.utils.MapUtils;

import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrieColumnSearchEngine implements ColumnSearchEngine {

    private PrefixTree prefixTree;
    private final TableParser tableParser;

    private Map<Integer, String> searchData;

    private final Clock clock = Clock.systemDefaultZone();

    public TrieColumnSearchEngine(TableParser tableParser, int column) throws IOException {
        this.tableParser = tableParser;

        prepareEngineForSearchInColumn(column);
    }

    @Override
    public void prepareEngineForSearchInColumn(int column) {
        searchData = tableParser.getColumnIdAndData(column);

        prefixTree = new PrefixTree();
        prefixTree.addMultipleData(searchData);
    }

    @Override
    public List<String> search(String query) {
        query = query.trim();

        if (query.trim().length() == 0) {
            throw new SearchException("Запрос должен содержать в себе непробельные символы!");
        }

        List<Integer> matchedIds = prefixTree.searchIdsStartingByString(query);

        Map<Integer, String> resultMap = filterSearchData(matchedIds);

        return formatResult(resultMap, matchedIds);
    }

    private Map<Integer, String> filterSearchData(List<Integer> ids) {
        MapUtils<Integer, String> mapUtils = new MapUtils<>();

        return mapUtils.filter(searchData, ids);
    }

    private List<String> formatResult(Map<Integer, String> resultMap, List<Integer> matchedIds) {
        List<String> resultList = new ArrayList<>();

        Map<Integer, String> rows = tableParser.getRowsAndIds(matchedIds);

        for (Integer id : matchedIds) {

            String singleResult = String.format("%s[%s]", resultMap.get(id), rows.get(id));

            resultList.add(singleResult);

        }

        return resultList;
    }
}
