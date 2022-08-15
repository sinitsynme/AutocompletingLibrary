package ru.sinitsynme.airportsearch.parser;

import java.util.Map;

public interface TableParser {

    Map<Integer, String> getColumnIdAndData(int column);

    String getRow(int id);
}
