package ru.sinitsynme.airportsearch;

import ru.sinitsynme.airportsearch.exception.ArgumentException;
import ru.sinitsynme.airportsearch.exception.SearchException;
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

    private static String filePath;

    private static final String terminalFilePath = "classes/airports.csv";
    private static final String consoleFilePath = "src/main/resources/airports.csv";

    private static ColumnSearchEngine searchEngine;

    private static int searchColumnNumber;


    public static void main(String[] args) {


        try {

//            runInConsole();
            runInTerminal(args);

            instantiateSearchEngine(searchColumnNumber);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Перезапустите программу.");
            return;
        }

        useSearchEngine();
    }

    private static void instantiateSearchEngine(int columnNumber) {

        CSVParser csvParser = new CSVParser(filePath);

        if (csvParser.isColumnString(columnNumber)) {
            searchEngine = new StringColumnSearchEngine(csvParser, columnNumber);
        } else searchEngine = new NumberColumnSearchEngine(csvParser, columnNumber);

    }

    private static void useSearchEngine() {
        System.out.printf("Поиск ведётся по колонке %d %n", searchColumnNumber);

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
                System.out.println("В процессе поиска возникла ошибка!");
                System.out.println(e.getMessage());
                System.out.print("Введите !quit , чтобы завершить работу программы, или нажмите ENTER: ");
                quit = scanner.nextLine();
            }

        }
    }

    private static void runInConsole() {

        filePath = consoleFilePath;
        System.out.println("Введите номер колонки поиска: ");

        try {
            searchColumnNumber = Integer.parseInt(scanner.nextLine());

        } catch (NumberFormatException nfe) {
            throw new ArgumentException("Разрешён ввод только числового аргумента!");
        }
    }

    private static void runInTerminal(String... args) {

        filePath = terminalFilePath;

        if (args.length == 0) {
            throw new ArgumentException("Необходим один числовой аргумент!");
        }

        if (args.length > 1) {
            throw new ArgumentException("Разрешён ввод только одного аргумента - колонки поиска!");
        }

        try {
            searchColumnNumber = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            throw new ArgumentException("Разрешён ввод только числового аргумента!");
        }
    }
}
