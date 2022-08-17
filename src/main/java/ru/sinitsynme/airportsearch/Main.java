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

    private static final String filePath = "classes/airports.csv";
//    private static final String filePath = "src/main/resources/airports.csv";

    private static final CSVParser csvParser = new CSVParser(filePath);

    private static ColumnSearchEngine searchEngine;


    public static void main(String[] args) {

//        int searchColumnNumber = 2;
        int searchColumnNumber;

        if (args.length == 0) {
            System.out.println("Необходим один числовой аргумент!");
            System.out.println("Перезапустите программу.");
            return;
        }

        if (args.length > 1) {
            System.out.println("Разрешён ввод только одного аргумента - колонки поиска!");
            System.out.println("Перезапустите программу.");
            return;
        }

        try {
            searchColumnNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("Разрешён ввод только числового аргумента!");
            System.out.println("Перезапустите программу.");
            return;
        }
        try {
            instantiateSearchEngine(searchColumnNumber);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Перезапустите программу.");
            return;
        }

        useSearchEngine();
    }

    private static void instantiateSearchEngine(int columnNumber) {


        if (csvParser.isColumnAString(columnNumber)) {
            searchEngine = new StringColumnSearchEngine(csvParser, columnNumber);
        } else searchEngine = new NumberColumnSearchEngine(csvParser, columnNumber);

    }

    private static void useSearchEngine() {

        String quit = "";

        while (!quit.equalsIgnoreCase("!quit")) {
            System.out.println("Введите строку:");
            String query = scanner.nextLine();

            long before = clock.millis();

            try {
                List<String> resultList = searchEngine.search(query);

                long after = clock.millis();

                for (String result : resultList) {
                    System.out.println(result);
                }

                System.out.printf("Количество найденных строк: %d%n", resultList.size());
                System.out.printf("Время поиска: %d ms%n%n", after - before);
                System.out.print("Введите !quit , чтобы завершить работу программы, или нажмите ENTER: ");
                quit = scanner.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.print("Введите !quit , чтобы завершить работу программы, или нажмите ENTER: ");
                quit = scanner.nextLine();
            }

        }
    }
}
