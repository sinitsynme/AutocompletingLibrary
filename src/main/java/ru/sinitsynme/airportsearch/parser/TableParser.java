package ru.sinitsynme.airportsearch.parser;

import java.util.List;
import java.util.Map;

public interface TableParser {

    boolean isColumnString(int column);
    Map<Integer, String> getColumnDataAndId(int column);

    String getRow(int id);

    Map<Integer, String> getDataByRowIds(List<Integer> idList);

}
