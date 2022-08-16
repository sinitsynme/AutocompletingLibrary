package ru.sinitsynme.airportsearch.parser;

import java.util.List;
import java.util.Map;

public interface TableParser {

    Map<Integer, String> getColumnIdAndData(int column);

    String getRow(int id);

    Map<Integer, String> getRowsAndIds(List<Integer> idList);
}
