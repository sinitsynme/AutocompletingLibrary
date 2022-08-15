package ru.sinitsynme.airportsearch.searchEngine;

import java.util.List;

public interface ColumnSearchEngine {
    void prepareEngineForSearchInColumn(int column);

    List<String> search(String query);

}
