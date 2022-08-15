package ru.sinitsynme.airportsearch.searchEngine.impl;

import ru.sinitsynme.airportsearch.exception.SearchException;
import ru.sinitsynme.airportsearch.parser.TableParser;
import ru.sinitsynme.airportsearch.prefixTree.PrefixTree;
import ru.sinitsynme.airportsearch.searchEngine.ColumnSearchEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TrieColumnSearchEngine implements ColumnSearchEngine {

    private PrefixTree prefixTree;
    private final TableParser tableParser;

    private Map<Integer, String> searchData;

    public TrieColumnSearchEngine(TableParser tableParser, int column) {
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

        if (query.trim().length() == 0){
            throw new SearchException("Запрос должен содержать в себе непробельные символы!");
        }

        List<Integer> matchedIds = prefixTree.searchIdsStartingByString(query);

        Map<Integer, String> resultMap = searchData.entrySet().stream().filter(x -> matchedIds.contains(x.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return formatResult(resultMap);
    }

    private List<String> formatResult(Map<Integer, String> resultMap)
    {
        List<String> resultList = new ArrayList<>();

        for (Map.Entry<Integer, String> entry: resultMap.entrySet()) {

            String singleResult = String.format("%s[%s]", entry.getValue(), tableParser.getRow(entry.getKey()) == null ? "" : tableParser.getRow(entry.getKey()));
            resultList.add(singleResult);

        }

        return resultList;
    }
}
