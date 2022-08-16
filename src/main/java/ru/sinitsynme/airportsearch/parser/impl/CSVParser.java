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

    private String filePath;

    public CSVParser(String filePath) {
        this.filePath = filePath;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Map<Integer, String> getColumnIdAndData(int column) {
        Map<Integer, String> map = new HashMap<>();

        try (FileReader fileReader = new FileReader(filePath)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String row = "";
            int i = 1;

            while ((row = bufferedReader.readLine()) != null) {
                String[] tableRow = row.split(",");

                if (column <= 0 || column > tableRow.length)
                    throw new TableParseException(String.format("В файле нет колонки с номером %d. Допустимые колонки: 1-%d %nПерезапустите программу.",
                            column, tableRow.length));

                map.put(i++, tableRow[column - 1].replaceAll("\"", ""));
            }
        } catch (IOException e) {
            throw new TableParseException("Указан неверный путь к файлу!");
        }

        return map;

    }

    @Override
    public String getRow(int id) {
        try (FileReader fileReader = new FileReader(filePath)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String row = "";
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
    public Map<Integer, String> getRowsAndIds(List<Integer> idList) {
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
