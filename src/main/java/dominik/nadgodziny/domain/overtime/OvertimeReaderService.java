package dominik.nadgodziny.domain.overtime;


import dominik.nadgodziny.domain.overtime.exception.ErrorMessages;
import dominik.nadgodziny.domain.overtime.exception.WrongArgumentInputException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@RequiredArgsConstructor
class OvertimeReaderService extends SwitchProcessorService implements OvertimeReader {

    private final OvertimeRepository overtimeRepository;

    private static final int MAX_MONT_NUMBER = 12;
    private static final int MIN_MONT_NUMBER = 1;

    @Override
    public List<Overtime> findAllOvertimes() {
        return overtimeRepository.findAll();
    }

    @Override
    public List<Overtime> findOvertimeByMonth(int month) {
        return findAllOvertimes().stream()
                .filter(o -> o.getOvertimeDate().getMonthValue() == month)
                .toList();
    }

    @Override
    public int getSumOfAllOvertimeHoursByMonth(int year, int month) {
        List<Overtime> allOvertimeByMonth = findOvertimeByMonth(month);
        return allOvertimeByMonth.stream()
                .filter(o -> o.getOvertimeDate().getYear() == year)
                .mapToInt(Overtime::getDuration)
                .sum();
    }

    @Override
    public int getSumOfHoursByGivenStatusOfGivenMonthAndGivenYear(int year, int month, String status) {
        List<Overtime> allOvertimeByMonth = findOvertimeByMonth(month);
        return allOvertimeByMonth.stream()
                .filter(o -> o.getOvertimeDate().getYear() == year)
                .filter(o -> o.getStatus().equals(status))
                .mapToInt(Overtime::getDuration)
                .sum();
    }


    public List<Overtime> getAllOvertimes() {
        ConsoleWriter.printText("\n\n\n\nLista wszystkich nadgodzin:");
        List<Overtime> allOvertimes = findAllOvertimes();
        isEmptyOrNot(allOvertimes);
        allOvertimes.forEach(System.out::println);
        return allOvertimes;
    }

    public List<Overtime> getOvertimeByMonth(Scanner scanner) {
        ConsoleWriter.printText("Podaj miesiac (1-12): ");
        int month = scanner.nextInt();
        isCorrectMonthNumber(month);
        List<Overtime> overtimeByMonth = findOvertimeByMonth(month);
        scanner.nextLine();
        ConsoleWriter.printText("\n\n\n");
        isEmptyOrNot(overtimeByMonth);
        overtimeByMonth.forEach(System.out::println);
        return overtimeByMonth;
    }

    public int getSumOfAllOvertimeHoursByMonthAndYear(Scanner scanner) {
        ConsoleWriter.printText("Podaj rok nadgodzin: ");
        int year = scanner.nextInt();
        ConsoleWriter.printText("Podaj miesiac");
        int month = scanner.nextInt();
        isCorrectMonthNumber(month);
        scanner.nextLine();
        int summing = getSumOfAllOvertimeHoursByMonth(year,month);
        if (summing == 0) {
            ConsoleWriter.printText("Nie znaleziono danych z miesiąca " + month);
        }
        ConsoleWriter.printText("Laczna suma godzin to " + summing);
        return summing;
    }

    public int getSumByGivenStatusOfGivenMonthWithYear(Scanner scanner) {
        try {
            ConsoleWriter.printText("Podaj rok nadgodzin: ");
            int year = scanner.nextInt();
            ConsoleWriter.printText("Podaj miesiac nadgodzin (1 -12): ");
            int month = scanner.nextInt();
            isCorrectMonthNumber(month);
            scanner.nextLine();
            ConsoleWriter.printText("Podaj rodzja nadgodzin (nadgodziny - zlecenie)");
            String selectedStatus = statusSelectionLoop(scanner);
            int sumResult = getSumOfHoursByGivenStatusOfGivenMonthAndGivenYear(year, month, selectedStatus);
            ConsoleWriter.printText("Liczba godzin to " + sumResult + " godzin");
            return sumResult;
        } catch (WrongArgumentInputException e) {
            ConsoleWriter.printText(e.getMessage());
        } catch (Exception e) {
            ConsoleWriter.printText("Wystąpił nieoczekiwany błąd: " + e.getMessage());
        }
        return 0;
    }

    void isCorrectMonthNumber(int month) {
        if (month < MIN_MONT_NUMBER || month > MAX_MONT_NUMBER) {
            ConsoleWriter.printText(ErrorMessages.WRONG_MONTH.getMessage());
        }
    }

    void isEmptyOrNot(List<Overtime> byMonthOvertimeDate) {
        if (byMonthOvertimeDate.isEmpty()) {
            ConsoleWriter.printText("Nie znaleziono danych ");
        }
    }
}
