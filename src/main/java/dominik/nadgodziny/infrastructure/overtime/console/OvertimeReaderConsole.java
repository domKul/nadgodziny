package dominik.nadgodziny.infrastructure.overtime.console;

import dominik.nadgodziny.domain.overtime.ConsoleWriter;
import dominik.nadgodziny.domain.overtime.OvertimeFacade;
import dominik.nadgodziny.domain.overtime.dto.OvertimeResponseDto;
import dominik.nadgodziny.domain.overtime.exception.ErrorMessages;
import dominik.nadgodziny.domain.overtime.exception.WrongArgumentInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
@Component
class OvertimeReaderConsole extends StatusSelectionProcessor {

    private final OvertimeFacade overtimeConsoleFacade;
    private static final int MAX_MONTH_NUMBER = 12;
    private static final int MIN_MONTH_NUMBER = 1;


    void findOvertimeByMonth(Scanner scanner) {
        try {
            int year = promptForYear(scanner);
            int month = promptForMonth(scanner);
            isCorrectMonthNumber(month, year);
            List<OvertimeResponseDto> overtimeResponseDtos = overtimeConsoleFacade.findByMonth(year, month);
            scanner.nextLine();
            ConsoleWriter.printText("\n\n");
            overtimeResponseDtos.forEach(System.out::println);
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
        } catch (Exception e) {
            ConsoleWriter.printText("Wystąpił nieoczekiwany błąd: " + e.getMessage());
        }
    }

    public void findAllSorted(){
        List<OvertimeResponseDto> overtimesFound = overtimeConsoleFacade.findAll();
        ConsoleWriter.printText("\n\nLista wszystkich nadgodzin:");
        overtimesFound.forEach(System.out::println);
    }

    void getSumOfAllOvertimeHoursByMonthAndYear(Scanner scanner) {
        try {
            int year = promptForYear(scanner);
            int month = promptForMonth(scanner);
            isCorrectMonthNumber(month, year);
            scanner.nextLine();
            int summing = overtimeConsoleFacade.sumOfAllOvertimeHoursByMonth(year, month);
            if (summing == 0) {
                ConsoleWriter.printText("Nie znaleziono danych z miesiąca " + month);
            } else {
                ConsoleWriter.printText("Łączna suma godzin to " + summing);
            }
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
        } catch (Exception e) {
            ConsoleWriter.printText("Wystąpił nieoczekiwany błąd: " + e.getMessage());
        }
    }

    void getSumByGivenStatusOfGivenMonthWithYear(Scanner scanner) {
        try {
            int year = promptForYear(scanner);
            int month = promptForMonth(scanner);
            isCorrectMonthNumber(month, year);
            scanner.nextLine();
            String selectedStatus = promptForStatus(scanner);
            int sumResult = overtimeConsoleFacade.sumByGivenStatusOfGivenMonth(year, month, selectedStatus);
            ConsoleWriter.printText("Liczba godzin to " + sumResult + " godzin");
        } catch (WrongArgumentInputException e) {
            ConsoleWriter.printText(e.getMessage());
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
        } catch (Exception e) {
            ConsoleWriter.printText("Wystąpił nieoczekiwany błąd: " + e.getMessage());
        }
    }

    public void findOvertimeByStatus(Scanner scanner) {
        try {
            int year = promptForYear(scanner);
            String selectedStatus = promptForStatus(scanner);
            List<OvertimeResponseDto> allOvertimesByStatus = overtimeConsoleFacade.findByStatusAndYear(year, selectedStatus);
            ConsoleWriter.printText("Znaleziono " + allOvertimesByStatus.size());
            allOvertimesByStatus.forEach(System.out::println);
        } catch (DataAccessException e) {
            ConsoleWriter.printText("Błąd dostępu do danych: " + e.getMessage());
        } catch (Exception e) {
            ConsoleWriter.printText("Wystąpił nieoczekiwany błąd: " + e.getMessage());
        }
    }

    private int promptForYear(Scanner scanner) {
        ConsoleWriter.printText("Podaj rok nadgodzin: ");
        return scanner.nextInt();
    }

    private int promptForMonth(Scanner scanner) {
        ConsoleWriter.printText("Podaj miesiąc (1-12): ");
        return scanner.nextInt();
    }

    private String promptForStatus(Scanner scanner) {
        ConsoleWriter.printText("Podaj rodzaj nadgodzin (nadgodziny - zlecenie)");
        return statusSelectionLoop(scanner);
    }

    private void isCorrectMonthNumber(int month, int year) {
        if (month < MIN_MONTH_NUMBER || month > MAX_MONTH_NUMBER || year > LocalDate.now().getYear()) {
            throw new WrongArgumentInputException(ErrorMessages.WRONG_YEAR_OR_MONTH.getMessage());
        }
    }
}
