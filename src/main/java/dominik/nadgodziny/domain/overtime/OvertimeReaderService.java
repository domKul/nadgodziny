package dominik.nadgodziny.domain.overtime;


import dominik.nadgodziny.domain.exception.ErrorMessages;
import dominik.nadgodziny.domain.exception.WrongArgumentInputException;

import java.util.List;
import java.util.Scanner;

import static dominik.nadgodziny.domain.overtime.ConsoleWriter.printText;

class OvertimeReaderService implements OvertimeReader {
    private final OvertimeRepository overtimeReader;

    OvertimeReaderService(OvertimeRepository overtimeReader) {
        this.overtimeReader = overtimeReader;
    }


    public void getAllOvertimes() {
        List<Overtime> allOvertimes = overtimeReader.findAllOvertimes();
        printText("\n\n\n\nLista wszystkich nadgodzin:");
        isEmptyOrNot(allOvertimes);
        allOvertimes.forEach(System.out::println);
    }

    public void getOvertimeByMonth(Scanner scanner) {
        printText("Podaj miesiac (1-12): ");
        int month = scanner.nextInt();
        scanner.nextLine();
        List<Overtime> overtimeByMonth = overtimeReader.findByMonthOvertimeDate(month);
        printText("\n\n\n\n\n");
        isEmptyOrNot(overtimeByMonth);
        overtimeByMonth.forEach(System.out::println);
    }

    public void getSumOfAllOvertimeHoursByMonth(Scanner scanner) {
        printText("Podaj miesiac");
        int month = scanner.nextInt();
        scanner.nextLine();
        List<Overtime> byMonthOvertimeDate = overtimeReader.findByMonthOvertimeDate(month);
        if (byMonthOvertimeDate.isEmpty()) {
            printText("Nie znaleziono danych z miesiąca " + month);
            return;
        }
        int sum = overtimeReader.countByDuration(month);
        printText("Laczna suma godzin to " + sum);
    }

    public void getSumByGivenStatusOfGivenMonth(Scanner scanner) {
        try {
            printText("podaj miesiac nadgodzin: ");
            int miesiac = scanner.nextInt();
            scanner.nextLine();
            printText("podaj rodzja nadgodzin");
            String rodzaj = scanner.nextLine();
            if (miesiac < 1 || miesiac > 12) {
                throw new WrongArgumentInputException(ErrorMessages.WRONG_MONTH.name());
            }
            int i = overtimeReader.countByDurationByStatus(miesiac, rodzaj);
            printText("liczba godzin to " + i);
        } catch (WrongArgumentInputException e) {
            printText(e.getMessage());
        } catch (Exception e) {
            printText("Wystąpił nieoczekiwany błąd: " + e.getMessage());
        }
    }

    void isEmptyOrNot(List<Overtime> byMonthOvertimeDate) {
        if (byMonthOvertimeDate.isEmpty()) {
            printText("Nie znaleziono danych ");
        }
    }
}
