package dominik.nadgodziny.domain.overtime;

import dominik.nadgodziny.domain.overtime.exception.ErrorMessages;
import dominik.nadgodziny.domain.overtime.exception.WrongArgumentInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
class OvertimeReaderService extends SwitchProcessorService implements OvertimeReader {

    private final OvertimeRepository overtimeRepository;

    private static final int MAX_MONT_NUMBER = 12;
    private static final int MIN_MONT_NUMBER = 1;

    @Override
    public List<Overtime> findAllOvertimes() {
        try {
            return overtimeRepository.findAll();
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<Overtime> findAllOvertimesByStatus(int year, String status) {
        try {
            return findAllOvertimes().stream()
                    .filter(o -> o.getOvertimeDate().getYear() == year)
                    .filter(o -> o.getStatus().equals(status))
                    .toList();
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public List<Overtime> findOvertimeByMonthAndYear(int year, int month) {
        try {
            return findAllOvertimes().stream()
                    .filter(o -> o.getOvertimeDate().getYear() == year)
                    .filter(o -> o.getOvertimeDate().getMonthValue() == month)
                    .toList();
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public int getSumOfAllOvertimeHoursByMonth(int year, int month) {
        try {
            List<Overtime> allOvertimeByMonth = findOvertimeByMonthAndYear(year, month);
            return allOvertimeByMonth.stream()
                    .mapToInt(Overtime::getDuration)
                    .sum();
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int getSumOfHoursByGivenStatusOfGivenMonthAndGivenYear(int year, int month, String status) {
        try {
            List<Overtime> allOvertimeByMonth = findOvertimeByMonthAndYear(year, month);
            return allOvertimeByMonth.stream()
                    .filter(o -> o.getStatus().equals(status))
                    .mapToInt(Overtime::getDuration)
                    .sum();
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return 0;
        }
    }

    public List<Overtime> getAllOvertimesSortedById() {
        try {
            ConsoleWriter.printText("\n\n\n\nLista wszystkich nadgodzin:");
            List<Overtime> allOvertimes = findAllOvertimes();
            isEmptyOrNot(allOvertimes);
            allOvertimes.stream()
                    .sorted(Comparator.comparingLong(Overtime::getId))
                    .forEach(System.out::println);
            return allOvertimes;
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return List.of();
        }
    }

    public List<Overtime> getOvertimeByMonthAndYear(Scanner scanner) {
        try {
            ConsoleWriter.printText("Podaj rok nadgodzin: ");
            int year = scanner.nextInt();
            ConsoleWriter.printText("Podaj miesiac (1-12): ");
            int month = scanner.nextInt();
            isCorrectMonthNumber(month);
            List<Overtime> overtimeByMonth = findOvertimeByMonthAndYear(year, month);
            scanner.nextLine();
            ConsoleWriter.printText("\n\n\n");
            isEmptyOrNot(overtimeByMonth);
            overtimeByMonth.forEach(System.out::println);
            return overtimeByMonth;
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return List.of();
        }
    }

    public int getSumOfAllOvertimeHoursByMonthAndYear(Scanner scanner) {
        try {
            ConsoleWriter.printText("Podaj rok nadgodzin: ");
            int year = scanner.nextInt();
            ConsoleWriter.printText("Podaj miesiac");
            int month = scanner.nextInt();
            isCorrectMonthNumber(month);
            scanner.nextLine();
            int summing = getSumOfAllOvertimeHoursByMonth(year, month);
            if (summing == 0) {
                ConsoleWriter.printText("Nie znaleziono danych z miesiąca " + month);
            }
            ConsoleWriter.printText("Laczna suma godzin to " + summing);
            return summing;
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return 0;
        }
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
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
        } catch (Exception e) {
            ConsoleWriter.printText("Wystąpił nieoczekiwany błąd: " + e.getMessage());
        }
        return 0;
    }

    public List<Overtime> getSumByGivenStatus(Scanner scanner){
        try {
            ConsoleWriter.printText("Podaj rok nadgodzin: ");
            int year = scanner.nextInt();
            ConsoleWriter.printText("Podaj rodzja nadgodzin (nadgodziny - zlecenie)");
            String selectedStatus = statusSelectionLoop(scanner);
            List<Overtime> allOvertimesByStatus = findAllOvertimesByStatus(year, selectedStatus);
            ConsoleWriter.printText("Znaleziono " + allOvertimesByStatus.size());
            allOvertimesByStatus.forEach(System.out::println);
            return allOvertimesByStatus;
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
            return List.of();
        }
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
