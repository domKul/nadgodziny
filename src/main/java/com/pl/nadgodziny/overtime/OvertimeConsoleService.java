package com.pl.nadgodziny.overtime;

import com.pl.nadgodziny.overtime.exception.WrongArgumentInputException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Service
public class OvertimeConsoleService {
    private final Scanner sc = new Scanner(System.in);
    private final ConfigurableApplicationContext applicationContext;
    private final OvertimeService overtimeService;

    OvertimeConsoleService(ConfigurableApplicationContext applicationContext, OvertimeService overtimeService) {
        this.applicationContext = applicationContext;
        this.overtimeService = overtimeService;
    }

    public void runApp() {
        try (sc){
            do {
                initialInfo();
                int nextInt;
                try {
                    nextInt = sc.nextInt();
                    sc.nextLine();
                } catch (InputMismatchException e) {
                    printText("Musisz wybrać cyfrę.");
                    sc.nextLine();
                    continue;
                }
                if (whatNext(nextInt)) return;
            } while (true);
        } catch (Exception e) {
            printText("Wystąpił błąd: " + e.getMessage());
        }
    }

     boolean whatNext(int nextInt) {
        try {
            switch (nextInt) {
                case 1 -> createOvertimeAndSaveToDb(sc);
                case 2 -> findAll();
                case 3 -> {
                    printText("Zamykanie aplikacji...");
                    applicationContext.close();
                    return true;
                }
                case 4 -> getOvertimeByMonth(sc);
                case 5 -> sumOfAllOvertimeHoursByMonth(sc);
                case 6 -> sumByGivenStatusOfGivenMonth(sc);
                default -> printText("Nieprawidłowa opcja. Wybierz od 1 do 6");
            }
        } catch (InputMismatchException e) {
            printText("Zle dane wejsciowe");
        }

        return false;
    }

    void printText(String txt) {
        System.out.println(txt);
    }

    void addNewOvertime(Overtime overtime) {
        try {
            overtimeService.addNewOvertime(overtime);
        } catch (Exception e) {
            throw new RuntimeException("Wystąpił błąd podczas zapisu nadgodzin do bazy danych.", e);
        }
    }

    void createOvertimeAndSaveToDb(Scanner scanner) {
        Overtime overtime = createOvertimeObject(scanner);
        addNewOvertime(overtime);

    }

    Overtime createOvertimeObject(Scanner scanner) {
        try {
            printText("Data nadgodzin w formacie RRRR-MM-DD:");
            String dateString = scanner.nextLine();
            LocalDate date = localDateFromString(dateString);
            printText("Rodzaj nadgodzin");
            String status = scanner.nextLine();
            printText("Czas pracy (w godzinach)");
            int hours = scanner.nextInt();
            return new Overtime(date, status, hours);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException(" Zły format daty.", e.getParsedString(), e.getErrorIndex());
        }
    }

    static LocalDate localDateFromString(String dateString) {
        return LocalDate.parse(dateString);
    }

    void findAll() {
        List<Overtime> allOvertimes = overtimeService.getAllOvertimes();
        printText("\n\n\n\nLista wszystkich nadgodzin:");
        isEmptyOrNot(allOvertimes);
        for (Overtime overtime : allOvertimes) {
            printText(overtime.toString());
        }
    }

    void getOvertimeByMonth(Scanner scanner) {
        printText("Podaj miesiac (1-12): ");
        int month = scanner.nextInt();
        //scanner.nextLine();
        List<Overtime> overtimeByMonth = overtimeService.findOvertimeByMonth(month);
        printText("\n\n\n\n\n");
        isEmptyOrNot(overtimeByMonth);
        overtimeByMonth.forEach(System.out::println);
    }

    void isEmptyOrNot(List<Overtime> byMonthOvertimeDate) {
        if (byMonthOvertimeDate.isEmpty()) {
            printText("Nie znaleziono danych ");
        }
    }

    void initialInfo() {
        printText("\n\n\nWybierz opcje" +
                " \n 1-dodaj " +
                "\n 2-wszystkie " +
                "\n 3-zakoncz " +
                "\n 4-znajdz po miesiacu" +
                "\n 5-suma godzin w danym miesiacu" +
                "\n 6-suma nadgodzin o z danego rodzaju w danym miesiacu");
    }

    void sumOfAllOvertimeHoursByMonth(Scanner scanner) {
        printText("Podaj miesiac");
        int month = scanner.nextInt();
        scanner.nextLine();
        List<Overtime> byMonthOvertimeDate = overtimeService.findOvertimeByMonth(month);
        if (byMonthOvertimeDate.isEmpty()) {
            printText("Nie znaleziono danych z miesiąca " + month);
            return;
        }
        int sum = overtimeService.sumDurationByMonth(month);
        printText("Laczna suma godzin to " + sum);
    }

     void sumByGivenStatusOfGivenMonth(Scanner scanner) {
        try {
            printText("podaj miesiac nadgodzin: ");
            int miesiac = scanner.nextInt();
            scanner.nextLine();
            printText("podaj rodzja nadgodzin");
            String rodzaj = scanner.nextLine();
            if (miesiac < 1 || miesiac > 12) {
                throw new WrongArgumentInputException("Błąd: Nieprawidłowy miesiąc.");
            }
            int i = overtimeService.sumByGivenStatusAndMonth(miesiac, rodzaj);
            printText("liczba godzin to " + i);
        } catch (WrongArgumentInputException e) {
            printText(e.getMessage());
        } catch (Exception e) {
            printText("Wystąpił nieoczekiwany błąd: " + e.getMessage());
        }
    }
}
