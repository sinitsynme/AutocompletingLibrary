package ru.sinitsynme.airportsearch.parser.impl;

import ru.sinitsynme.airportsearch.exception.TableParseException;
import ru.sinitsynme.airportsearch.parser.TableParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVParser implements TableParser {

    private final String filePath;

    private static final String CSV_SEPARATOR = ",";
    private static final String NULL_ATTRIBUTE = "\\N";
    private static final String STRING_ATTRIBUTE = "\"";
    private static final String EMPTY_STRING = "";

    public CSVParser(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    private static void checkIfThereIsColumnInTable(int column, String[] tableRow) {
        if (column <= 0 || column > tableRow.length)
            throw new TableParseException(
                    String.format("В файле нет колонки с номером %d. Допустимые колонки: 1-%d %n",
                    column, tableRow.length));
    }



    @Override
    public boolean isColumnString(int column) {
        try (FileReader fileReader = new FileReader(filePath)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String row = bufferedReader.readLine();

            if(row == null) return false;

            String[] tableRow = row.split(CSV_SEPARATOR);
            checkIfThereIsColumnInTable(column, tableRow);

            String checker = tableRow[column-1];

            return checker.contains(STRING_ATTRIBUTE);

        }
        catch (IOException e) {
            throw new TableParseException("Указан неверный путь к файлу!");
        }
    }

    @Override
    public Map<Integer, String> getColumnDataAndId(int column) {
        Map<Integer, String> possibleSearchResults = new HashMap<>();

        try (FileReader fileReader = new FileReader(filePath)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // Checking a column number by the first row

            String[] firstTableRow = bufferedReader.readLine().split(CSV_SEPARATOR);

            checkIfThereIsColumnInTable(column, firstTableRow);

            String firstRowValue = firstTableRow[column - 1].replaceAll(STRING_ATTRIBUTE, EMPTY_STRING);
            possibleSearchResults.put(1, firstRowValue);


            // Adding other rows in search results ignoring null values

            int wordRowId = 2;
            String row;

            while ((row = bufferedReader.readLine()) != null) {
                String[] tableRow = row.split(CSV_SEPARATOR);

                String rowValue = tableRow[column - 1].replaceAll(STRING_ATTRIBUTE, EMPTY_STRING);

                // Null values are not added in the list of possible search results - they cannot be found by a prefix
                if(rowValue.equalsIgnoreCase(NULL_ATTRIBUTE)) {
                    wordRowId++;
                    continue;
                }

                // Add value to search results map
                possibleSearchResults.put(wordRowId++, rowValue);
            }
        } catch (IOException e) {
            throw new TableParseException("Указан неверный путь к файлу!");
        }

        return possibleSearchResults;

    }

    @Override
    public String getRow(int id) {
        try (FileReader fileReader = new FileReader(filePath)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String row;
            int i = 1;
            while ((row = bufferedReader.readLine()) != null) {
                if (id == i++) break;
            }
            return row;
        } catch (IOException e) {
            throw new TableParseException("Указан неверный путь к файлу");
        }
    }

    @Override
    public Map<Integer, String> getDataByRowIds(List<Integer> idList) {
        Map<Integer, String> result = new HashMap<>();

        idList.sort(Integer::compareTo);

        int listIndex = 0;

        try (FileReader fileReader = new FileReader(filePath)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String row;
            int i = 1;
            while (listIndex < idList.size() && (row = bufferedReader.readLine()) != null) {

                int rowId = idList.get(listIndex);

                if (rowId == i++) {
                    result.put(rowId, row);
                    listIndex++;
                }
            }
            return result;
        } catch (IOException e) {
            throw new TableParseException("Указан неверный путь к файлу");
        }
    }
}
