package com.pl.nadgodziny.service;

import com.pl.nadgodziny.exception.WrongArgumentInputException;
import com.pl.nadgodziny.model.Overtime;
import com.pl.nadgodziny.repository.OvertimeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Service
public class OvertimeConsoleService {
    private Scanner sc = new Scanner(System.in);
    private final OvertimeRepository overtimeRepository;

    public OvertimeConsoleService(OvertimeRepository overtimeRepository) {
        this.overtimeRepository = overtimeRepository;
    }

    public void runApp() {
        try {
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
        } finally {
            sc.close();
        }
    }

    private boolean whatNext(int nextInt) {
        try {
            switch (nextInt) {
                case 1 -> createOvertimeandSaveToDb(sc);
                case 2 -> findAll();
                case 3 -> {
                    printText("Zamykanie aplikacji...");
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

    private void printText(String txt) {
        System.out.println(txt);
    }

    @Transactional
    public void addNewOvertime(Overtime overtime) {
        overtimeRepository.save(overtime);
    }

    private void createOvertimeandSaveToDb(Scanner scanner) {
        try {
            Overtime overtime = createOvertimeObject(scanner);
            addNewOvertime(overtime);
        } catch (DateTimeParseException e) {
            printText(" Zły format daty.");
        } catch (WrongArgumentInputException e) {
            printText("Błąd: Nieprawidłowe dane wejściowe." + e);
        } catch (Exception e) {
            printText("Wystąpił nieoczekiwany błąd: " + e.getMessage());
        }
    }

    private Overtime createOvertimeObject(Scanner scanner) {
        printText("Data nadgodzin w formacie RRRR-MM-DD:");
        String dateString = scanner.nextLine();
        LocalDate date = localDateFromString(dateString);
        printText("Rodzaj nadgodzin");
        String status = scanner.nextLine();
        printText("Czas pracy (w godzinach)");
        int hours = scanner.nextInt();
        return new Overtime(date, status, hours);
    }

    private static LocalDate localDateFromString(String dateString) {
        return LocalDate.parse(dateString);
    }

    private void findAll() {
        Iterable<Overtime> allOvertimes = overtimeRepository.findAll();
        printText("\n\n\n\nLista wszystkich nadgodzin:");

        for (Overtime overtime : allOvertimes) {
            printText(overtime.toString());
        }
    }

    private void getOvertimeByMonth(Scanner scanner) {
        printText("Podaj miesiac (1-12): ");
        int month = scanner.nextInt();
        scanner.nextLine();
        List<Overtime> byMonthOvertimeDate = overtimeRepository.findByMonthOvertimeDate(month);
        printText("\n\n\n\n\n");
        if (byMonthOvertimeDate.isEmpty()) {
            printText("Nie znaleziono danych z miesiaca " + month);
        }
        for (Overtime overtime : byMonthOvertimeDate) {
            printText(overtime.toString());
        }
    }


    private void initialInfo() {
        printText("\n\n\nWybierz opcje" +
                " \n 1-dodaj " +
                "\n 2-wszystkie " +
                "\n 3-zakoncz " +
                "\n 4-znajdz po miesiacu" +
                "\n 5-suma godzin w danym miesiacu" +
                "\n 6-suma nadgodzin o z danego rodzaju w danym miesiacu");
    }

    private void sumOfAllOvertimeHoursByMonth(Scanner scanner) {
        printText("Podaj miesiac");
        int month = scanner.nextInt();
        scanner.nextLine();

        List<Overtime> byMonthOvertimeDate = overtimeRepository.findByMonthOvertimeDate(month);

        if (byMonthOvertimeDate.isEmpty()) {
            printText("Nie znaleziono danych z miesiąca " + month);
            return;
        }

        int sum = overtimeRepository.countByDuration(month);
        printText("Laczna suma godzin to " + sum);
    }

    private void sumByGivenStatusOfGivenMonth(Scanner scanner) {
        try {
            printText("podaj miesiac nadgodzin: ");
            int miesiac = scanner.nextInt();
            scanner.nextLine();
            printText("podaj rodzja nadgodzin");
            String rodzaj = scanner.nextLine();
            if (miesiac < 1 || miesiac > 12) {
                throw new WrongArgumentInputException("Błąd: Nieprawidłowy miesiąc.");
            }
            int i = overtimeRepository.coundByDurationBystatus(miesiac, rodzaj);
            printText("liczba godzin to " + i);
        } catch (WrongArgumentInputException e) {
            printText(e.getMessage());
        } catch (Exception e) {
            printText("Wystąpił nieoczekiwany błąd: " + e.getMessage());
        }
    }


}
