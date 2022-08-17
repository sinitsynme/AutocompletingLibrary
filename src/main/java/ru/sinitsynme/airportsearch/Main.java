package ru.sinitsynme.airportsearch;

import ru.sinitsynme.airportsearch.parser.impl.CSVParser;
import ru.sinitsynme.airportsearch.searchEngine.ColumnSearchEngine;
import ru.sinitsynme.airportsearch.searchEngine.impl.NumberColumnSearchEngine;
import ru.sinitsynme.airportsearch.searchEngine.impl.StringColumnSearchEngine;

import java.time.Clock;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final Clock clock = Clock.systemDefaultZone();

    //    private static final String filePath = "classes/airports.csv";
    private static final String filePath = "src/main/resources/airports.csv";

    private static final CSVParser csvParser = new CSVParser(filePath);

    private static ColumnSearchEngine searchEngine;


    public static void main(String[] args) {

//        исключение - не ввели аргументов!

//        if(args.length > 1){
//            System.out.println("Разрешён ввод только одного аргумента - колонки поиска!");
//            System.out.println("Перезапустите программу!");
//            return;
//        }

//        int columnNumber = args[0];
        int columnNumber = 2;

        try {

            if (csvParser.isColumnAString(columnNumber)) {
                searchEngine = new StringColumnSearchEngine(csvParser, columnNumber);
            } else searchEngine = new NumberColumnSearchEngine(csvParser, columnNumber);

            useSearchEngine();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void useSearchEngine() {

        String quit = "";

        while (!quit.equalsIgnoreCase("!quit")) {
            System.out.println("Введите строку:");
            String query = scanner.nextLine();

            long before = clock.millis();

            List<String> resultList = searchEngine.search(query);

            long after = clock.millis();

            for (String result : resultList) {
                System.out.println(result);
            }

            System.out.printf("Число результатов: %d%n", resultList.size());
            System.out.printf("Время поиска вместе с фильтрацией: %d ms%n", after - before);
            System.out.print("Введите !quit , чтобы завершить работу программы, или нажмите ENTER: ");
            quit = scanner.nextLine();
        }
    }
}
